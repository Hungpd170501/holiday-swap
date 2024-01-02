package com.example.holidayswap.domain.entity.chat;

import com.example.holidayswap.domain.entity.common.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Conversation extends BaseEntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "conversation_id"
    )
    private Long conversationId;

    @Column
    private String conversationName;

    @Enumerated(EnumType.STRING)
    @Column(name = "conversation_type")
    private ConversationType conversationType;

    @OneToMany(mappedBy = "conversation")
    private List<ConversationParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>();
}
