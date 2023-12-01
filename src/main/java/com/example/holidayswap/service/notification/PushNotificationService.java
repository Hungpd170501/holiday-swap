package com.example.holidayswap.service.notification;

import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.notification.NotificationResponse;

import java.util.List;

public interface PushNotificationService {
    List<NotificationResponse> getAllNotificationsByCurrentUser();

    NotificationResponse sendNotificationToUser(NotificationRequest notificationRequest);

    void markNotificationAsReadByNotificationId(Long notificationId);

    void markAllNotificationsAsReadByCurrentUser();

    void deleteNotificationByNotificationId(Long notificationId);

    void deleteAllNotificationsByCurrentUser();

    void createNotification(NotificationRequest notificationRequest);

    void markCurrentUserNotificationAsReadByNotificationId(Long notificationId);
}
