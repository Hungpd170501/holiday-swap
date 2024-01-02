package com.example.holidayswap.utils;

import com.example.holidayswap.domain.entity.auth.RoleName;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.repository.chat.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatUtils {
    private final ConversationRepository conversationRepository;
    public boolean isUserNotInConversation(Long userId, Long conversationId) {
        return conversationRepository.findByUserIdEqualsAndConversationIdEquals(userId, conversationId).isEmpty();
    }

    public boolean isStaff(User user) {
        return user.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.Staff.name()));
    }

}
