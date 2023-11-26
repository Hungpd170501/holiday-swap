package com.example.holidayswap.service.chat;

import com.example.holidayswap.domain.dto.request.chat.ConversationRequest;
import com.example.holidayswap.domain.dto.response.chat.ConversationParticipantResponse;
import com.example.holidayswap.domain.dto.response.chat.ConversationResponse;
import com.example.holidayswap.domain.entity.chat.Conversation;
import com.example.holidayswap.domain.entity.chat.ConversationParticipant;
import com.example.holidayswap.domain.entity.chat.ConversationParticipantPK;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.chat.ConversationParticipantMapper;
import com.example.holidayswap.domain.mapper.chat.MessageMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.chat.ConversationParticipantRepository;
import com.example.holidayswap.repository.chat.ConversationRepository;
import com.example.holidayswap.repository.chat.MessageRepository;
import com.example.holidayswap.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ConversationParticipantRepository conversationParticipantRepository;
    private final UserRepository userRepository;
    private final AuthUtils authUtils;
    private final MessageMapper messageMapper;

    @Override
    public List<ConversationResponse> getUserConversations() {
        var user = authUtils.getAuthenticatedUser();
        return conversationRepository.findByUserId(user.getUserId()).stream().map(conversation -> {
            var latestMessage = messageRepository.findLatestMessageByConversation(conversation.getConversationId());
            return ConversationResponse.builder()
                    .conversationId(conversation.getConversationId())
                    .creationDate(conversation.getCreatedOn())
                    .conversationName(conversation.getConversationName())
                    .participants(conversation.getParticipants().stream()
                            .map(ConversationParticipantMapper.INSTANCE::toConversationParticipantResponse)
                            .toList())
                    .message(latestMessage.map(messageMapper::toMessageResponse).orElse(null))
                    .build();
        }).sorted((o1, o2) -> {
            LocalDateTime date1 = o1.getMessage() != null ? o1.getMessage().getCreatedOn() : o1.getCreationDate();
            LocalDateTime date2 = o2.getMessage() != null ? o2.getMessage().getCreatedOn() : o2.getCreationDate();
            return date2.compareTo(date1);
        }).toList();
    }

    @Override
    @Transactional
    public void createConversation(ConversationRequest conversationRequest) {
        Conversation conversation = Conversation.builder()
                .conversationName(conversationRequest.getConversationName())
                .build();
        conversationRepository.save(conversation);
        for (Long userId : conversationRequest.getUserIds()) {
            var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
            ConversationParticipant participant = new ConversationParticipant();
            ConversationParticipantPK participantPK = new ConversationParticipantPK();
            participantPK.setConversationId(conversation.getConversationId());
            participantPK.setUserId(user.getUserId());
            participant.setConversationParticipantId(participantPK);
            participant.setConversation(conversation);
            participant.setUser(user);
            conversationParticipantRepository.save(participant);
        }
    }

    @Override
    public List<ConversationParticipantResponse> getConversationParticipants(Long conversationId) {
        return conversationParticipantRepository.findByConversationId(conversationId).stream()
                .map(ConversationParticipantMapper.INSTANCE::toConversationParticipantResponse)
                .toList();
    }
}
