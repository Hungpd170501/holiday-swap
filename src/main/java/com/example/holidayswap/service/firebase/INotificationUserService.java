package com.example.holidayswap.service.firebase;

import com.example.holidayswap.domain.entity.notification.NotificationEnum;
import com.example.holidayswap.domain.entity.notification.NotificationUser;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

public interface INotificationUserService {
    List<NotificationUser> GetAllNotificationByUserLogin();

    void CreateNotificationByUserId(Long userId,String title,String content, String link) throws FirebaseMessagingException;

    void IsReadNotificationByUserLogin();

    int CountNotificationNotReadByUserLogin();
}
