package com.example.holidayswap.service.chat;

import com.example.holidayswap.domain.dto.request.chat.ConversationRequest;
import com.example.holidayswap.domain.dto.response.chat.ConversationParticipantResponse;
import com.example.holidayswap.domain.dto.response.chat.ConversationResponse;

import java.util.List;
import java.util.Optional;

public interface ConversationService {
    List<ConversationResponse> getUserConversations();

    void createConversation(ConversationRequest conversationRequest);

    List<ConversationParticipantResponse> getConversationParticipants(Long conversationId);

    ConversationResponse getCurrentConverastionWithUserId(Long userId);

    Optional<ConversationResponse> createCurrentConversationWithUserId(Long userId);
}
