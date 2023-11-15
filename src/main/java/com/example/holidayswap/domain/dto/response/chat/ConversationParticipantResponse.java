package com.example.holidayswap.domain.dto.response.chat;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data

public class ConversationParticipantResponse {

    private boolean leftChat;

    @JsonIgnoreProperties({"email_verified", "phone_verified", "phone"})
    private UserProfileResponse user;
}
