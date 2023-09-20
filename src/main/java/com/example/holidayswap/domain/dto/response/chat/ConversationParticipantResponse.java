package com.example.holidayswap.domain.dto.response.chat;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.chat.Conversation;
import lombok.Data;

@Data

public class ConversationParticipantResponse {

    private boolean leftChat = false;

    private UserProfileResponse user;
}
