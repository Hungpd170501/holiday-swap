package com.example.holidayswap.utils;

import com.example.holidayswap.repository.chat.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatUtils {
    private final ConversationRepository conversationRepository;
    public boolean isUserInConversation(Long userId, Long conversationId) {
        return conversationRepository.findByUserIdEqualsAndConversationIdEquals(userId, conversationId).isEmpty();
    }
}
