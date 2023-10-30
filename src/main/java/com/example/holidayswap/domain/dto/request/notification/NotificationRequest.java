package com.example.holidayswap.domain.dto.request.notification;

import lombok.Data;

@Data
public class NotificationRequest {
    private String subject;
    private String content;
    private String href;
    private Long toUserId;
}
