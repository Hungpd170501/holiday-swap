package com.example.holidayswap.domain.entity.notification;

import com.example.holidayswap.domain.entity.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "notification_id"
    )
    private Long notificationId;

    @Column
    private String subject;

    @Column
    private String content;

    @Column
    private String href;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column
    private Boolean isRead;

    @Column
    private Boolean isDeleted;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
