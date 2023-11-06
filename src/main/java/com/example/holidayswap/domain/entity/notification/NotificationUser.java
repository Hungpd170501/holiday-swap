package com.example.holidayswap.domain.entity.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification_user")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotificationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "link")
    private String link;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "content")
    private String content;


}
