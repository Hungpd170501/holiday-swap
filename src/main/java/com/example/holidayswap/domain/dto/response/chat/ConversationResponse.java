package com.example.holidayswap.domain.dto.response.chat;

import com.example.holidayswap.domain.entity.chat.ConversationParticipant;
import com.example.holidayswap.domain.entity.chat.Message;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ConversationResponse {
    private Long conversationId;
    private LocalDateTime creationDate;
    private List<ConversationParticipantResponse> participants;
    private MessageResponse message;
}
