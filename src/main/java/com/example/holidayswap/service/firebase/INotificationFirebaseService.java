package com.example.holidayswap.service.firebase;

import com.example.holidayswap.domain.entity.notification.NotificationFirebase;

import java.util.List;

public interface INotificationFirebaseService {
    List<NotificationFirebase> GetAllNotificationFirebaseByUserId(Long userId);

    void CreateNotificationFirebaseByUserLogin(String fcmToken);

    void DeleteNotificationFirebaseByUserLoginAndFCMToken(String fcmToken);
}
