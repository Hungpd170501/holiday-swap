package com.example.holidayswap.service.chat;

import com.example.holidayswap.domain.dto.response.chat.ConversationParticipantResponse;
import com.example.holidayswap.domain.dto.response.chat.ConversationResponse;

import java.util.List;

public interface ConversationService {
    List<ConversationResponse> getUserConversations();

    void createConversation(List<Long> userIds);

    List<ConversationParticipantResponse> getConversationParticipants(Long conversationId);
}
