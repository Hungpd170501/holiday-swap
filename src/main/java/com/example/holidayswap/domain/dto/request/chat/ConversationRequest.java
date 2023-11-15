package com.example.holidayswap.domain.dto.request.chat;

import lombok.Data;

import java.util.List;

@Data
public class ConversationRequest {
    private String conversationName;
    private List<Long> userIds;
}
