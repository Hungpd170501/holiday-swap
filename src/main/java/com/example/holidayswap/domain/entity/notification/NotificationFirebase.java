package com.example.holidayswap.domain.entity.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification_firebase")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationFirebase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "user_id")
    private Long userId;
}
