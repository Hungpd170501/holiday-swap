package com.example.holidayswap.domain.entity.chat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "message_id"
    )
    private Long messageId;

    @Column(name = "text")
    private String text;

    @Column(name = "image")
    private String image;

    @Column(name = "date", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;
}
