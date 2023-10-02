package com.example.holidayswap.domain.dto.response.chat;

import com.example.holidayswap.domain.entity.chat.MessageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponse {
    private Long messageId;
    private String text;
    private String image;
    private LocalDateTime createdOn;
    private Long authorId;
    private MessageType messageType;
}
