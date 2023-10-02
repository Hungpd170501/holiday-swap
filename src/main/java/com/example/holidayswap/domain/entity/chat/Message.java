package com.example.holidayswap.domain.entity.chat;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "author_id")
    private Long authorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;
}
