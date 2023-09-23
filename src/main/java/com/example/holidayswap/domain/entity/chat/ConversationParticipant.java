package com.example.holidayswap.domain.entity.chat;

import com.example.holidayswap.domain.entity.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "conversation_participant")
public class ConversationParticipant {
    @EmbeddedId
    private ConversationParticipantPK conversationParticipantId;

    @Column(name = "left_chat", columnDefinition = "boolean default false")
    private boolean leftChat = false;

    @MapsId("conversationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
