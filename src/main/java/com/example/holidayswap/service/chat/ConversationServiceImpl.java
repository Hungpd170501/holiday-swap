package com.example.holidayswap.service.chat;

import com.example.holidayswap.domain.dto.request.chat.ConversationRequest;
import com.example.holidayswap.domain.dto.response.chat.ConversationParticipantResponse;
import com.example.holidayswap.domain.dto.response.chat.ConversationResponse;
import com.example.holidayswap.domain.entity.chat.Conversation;
import com.example.holidayswap.domain.entity.chat.ConversationParticipant;
import com.example.holidayswap.domain.entity.chat.ConversationParticipantPK;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.chat.ConversationMapper;
import com.example.holidayswap.domain.mapper.chat.ConversationParticipantMapper;
import com.example.holidayswap.domain.mapper.chat.MessageMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.chat.ConversationParticipantRepository;
import com.example.holidayswap.repository.chat.ConversationRepository;
import com.example.holidayswap.repository.chat.MessageRepository;
import com.example.holidayswap.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
    private final SimpMessagingTemplate messagingTemplate;
    private final ConversationMapper conversationMapper;

    @Override
    public List<ConversationResponse> getUserConversations() {
        var user = authUtils.getAuthenticatedUser();
        return conversationRepository.findByUserId(user.getUserId())
                .stream()
                .map(this::mapToConversationResponse)
                .sorted(Comparator.comparing(this::getSortDate).reversed())
                .toList();
    }

    private ConversationResponse mapToConversationResponse(Conversation conversation) {
        var latestMessage = messageRepository.findLatestMessageByConversation(conversation.getConversationId());
        return ConversationResponse.builder()
                .conversationId(conversation.getConversationId())
                .creationDate(conversation.getCreatedOn())
                .conversationName(conversation.getConversationName())
                .participants(conversation.getParticipants()
                        .stream()
                        .map(conversationParticipant -> {
                            var conversationParticipantResponse = ConversationParticipantMapper.INSTANCE
                                    .toConversationParticipantResponse(conversationParticipant);
                            conversationParticipantResponse.setCountUnreadMessages(conversationParticipant.countUnreadMessages());
                            return conversationParticipantResponse;
                        })
                        .toList())
                .message(latestMessage.map(messageMapper::toMessageResponse).orElse(null))
                .build();
    }

    private LocalDateTime getSortDate(ConversationResponse conversation) {
        return conversation.getMessage() != null ? conversation.getMessage().getCreatedOn() : conversation.getCreationDate();
    }

    @Override
    @Transactional
    public void createConversation(ConversationRequest conversationRequest) {
        List<Long> userIds = conversationRequest.getUserIds();
        if (userIds.size() > 2 && (conversationRequest.getConversationName() == null || conversationRequest.getConversationName().isEmpty())) {
            throw new DataIntegrityViolationException("Group conversation name is required");
        } else if (userIds.size() == 2 && conversationRepository.findConversationByUserIds(userIds.get(0), userIds.get(1)).isPresent()) {
            throw new DataIntegrityViolationException("Conversation already exists");
        }
        Conversation conversation = Conversation.builder()
                .conversationName(conversationRequest.getConversationName())
                .build();
        conversationRepository.save(conversation);
        createConversationParticipants(conversation, userIds);
        var updatedConversation = conversationRepository.findByConversationId(conversation.getConversationId())
                .map(this::mapToConversationResponse);
        if (updatedConversation.isEmpty()) {
            throw new EntityNotFoundException("Conversation not found");
        }
        CompletableFuture.runAsync(() -> {
            userIds.forEach(id ->
                    messagingTemplate.convertAndSend("/queue/new-conversation-" + id,
                            updatedConversation
                    ));
        });
    }

    @Override
    public List<ConversationParticipantResponse> getConversationParticipants(Long conversationId) {
        return conversationParticipantRepository.findByConversationId(conversationId)
                .stream()
                .map(ConversationParticipantMapper.INSTANCE::toConversationParticipantResponse)
                .toList();
    }

    @Override
    public ConversationResponse getCurrentConverastionWithUserId(Long userId) {
        var user = authUtils.getAuthenticatedUser();
        var conversation = conversationRepository.findConversationByUserIds(user.getUserId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("No contact found with this user."));
        return ConversationResponse.builder()
                .conversationId(conversation.getConversationId())
                .build();
    }

    @Override
    @Transactional
    public Optional<ConversationResponse> createCurrentConversationWithUserId(Long userId) {
        var currentUser = authUtils.getAuthenticatedUser();
        conversationRepository.findConversationByUserIds(currentUser.getUserId(), userId)
                .ifPresent(conversation -> {
                    throw new DataIntegrityViolationException("Conversation already exists");
                });
        var conversation =conversationRepository.save(Conversation.builder()
                .build());
        var userIds = List.of(currentUser.getUserId(), userId);
        createConversationParticipants(conversation, userIds);
        var updatedConversation = conversationRepository.findByConversationId(conversation.getConversationId());
        if (updatedConversation.isEmpty()) {
            throw new EntityNotFoundException("Conversation not found");
        }
        var conversationResponse = mapToConversationResponse(updatedConversation.get());
        CompletableFuture.runAsync(() -> userIds.forEach(id ->
                messagingTemplate.convertAndSend("/queue/new-conversation-" + id,
                        conversationResponse)));
        return Optional.ofNullable(conversationResponse);
    }

    private void createConversationParticipants(Conversation conversationEntity, List<Long> userIds) {
        List<ConversationParticipant> participants = new ArrayList<>();
        userIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)))
                .forEach(user -> {
                    ConversationParticipant participant = new ConversationParticipant();
                    ConversationParticipantPK participantPK = new ConversationParticipantPK();
                    participantPK.setConversationId(conversationEntity.getConversationId());
                    participantPK.setUserId(user.getUserId());
                    participant.setConversationParticipantId(participantPK);
                    participant.setConversation(conversationEntity);
                    participant.setUser(user);
                    var newParticipant = conversationParticipantRepository.save(participant);
                    participants.add(newParticipant);
                });
        conversationEntity.setParticipants(participants);    }
}
