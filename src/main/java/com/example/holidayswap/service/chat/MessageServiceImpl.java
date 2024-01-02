package com.example.holidayswap.service.chat;

import com.example.holidayswap.domain.dto.request.chat.MessageRequest;
import com.example.holidayswap.domain.dto.response.chat.MessageResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.chat.ConversationParticipant;
import com.example.holidayswap.domain.entity.chat.ConversationParticipantPK;
import com.example.holidayswap.domain.entity.chat.Message;
import com.example.holidayswap.domain.entity.chat.MessageType;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.mapper.chat.MessageMapper;
import com.example.holidayswap.repository.chat.ConversationParticipantRepository;
import com.example.holidayswap.repository.chat.ConversationRepository;
import com.example.holidayswap.repository.chat.MessageRepository;
import com.example.holidayswap.service.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final MessageMapper messageMapper;
    private final FileService fileService;
    private final ConversationParticipantRepository conversationParticipantRepository;

    @Override
    public List<MessageResponse> getConversationMessages(Long conversationId) {
        return messageRepository.findAllByConversationIdEquals(conversationId).stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }

    @Override
    public MessageResponse createMessage(MessageRequest messageRequest, String conversationId) throws IOException {
        if(messageRequest.getText().isBlank() && messageRequest.getImage() == null) {
            throw new DataIntegrityViolationException( "Message cannot be null");
        }
        Message message = messageMapper.toMessage(messageRequest);
        message.setMessageType(MessageType.TEXT);
        message.setConversation(conversationRepository.findById(Long.valueOf(conversationId))
                .orElseThrow(EntityNotFoundException::new));
        if (messageRequest.getImage() != null) {
            message.setImage(fileService.uploadFile(messageRequest.getImage()));
            message.setMessageType(MessageType.TEXT_AND_IMAGE);
        }
        messageRepository.save(message);
        return messageMapper.toMessageResponse(message);
    }

    @Override
    public void markAllAsRead(Long userId, Long conversationId) {
        var message = messageRepository.findLatestMessageByConversation(conversationId);
        var conversationParticipant = conversationParticipantRepository.findByUserIdAndConversationId(userId, conversationId);
        conversationParticipant.ifPresent(participant -> {
            participant.setMessageId(message.map(Message::getMessageId).orElse(0L));
            conversationParticipantRepository.save(participant);
        });
    }

    @Override
    public void createConversationParticipantIfNotExist(User user, Long conversationId) {
        var conversationParticipant = conversationParticipantRepository.findByUserIdAndConversationId(user.getUserId(), conversationId);
        if(conversationParticipant.isEmpty()) {
            var conversationParticipantPK = new ConversationParticipantPK();
            conversationParticipantPK.setUserId(user.getUserId());
            conversationParticipantPK.setConversationId(conversationId);
            conversationParticipantRepository.save(ConversationParticipant.builder()
                    .conversationParticipantId(conversationParticipantPK)
                            .user(user)
                            .conversation(conversationRepository.findById(conversationId).orElse(null))
                    .build());
        }
    }

    @Override
    public void markReadByMessageId(long userId, long conversationId, long messageId) {
        var conversationParticipant = conversationParticipantRepository.findByUserIdAndConversationId(userId, conversationId);
        conversationParticipant.ifPresent(participant -> {
            participant.setMessageId(messageId);
            conversationParticipantRepository.save(participant);
        });
    }
}
