package com.example.holidayswap.domain.dto.response.notification;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {
    private Long notificationId;
    private String subject;
    private String content;
    private String href;
    private LocalDateTime createdOn;
    private Boolean isRead;
}
