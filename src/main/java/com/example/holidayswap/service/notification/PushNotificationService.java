package com.example.holidayswap.service.notification;

import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.notification.NotificationResponse;

import java.util.List;

public interface PushNotificationService {
    List<NotificationResponse> GetAllNotificationsByCurrentUser();

    NotificationResponse SendNotificationToUser(NotificationRequest notificationRequest);

    void MarkNotificationAsReadByNotificationId(Long notificationId);

    void MarkAllNotificationsAsReadByCurrentUser();

    void DeleteNotificationByNotificationId(Long notificationId);

    void DeleteAllNotificationsByCurrentUser();
}
