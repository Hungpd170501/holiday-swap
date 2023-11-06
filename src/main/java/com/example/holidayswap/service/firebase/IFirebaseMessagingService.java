package com.example.holidayswap.service.firebase;

import com.example.holidayswap.domain.dto.response.firebase.NotificationMessage;

public interface IFirebaseMessagingService {
    String sendNotificationByToken(NotificationMessage notificationMessage) ;

    default void hi() {
        System.out.println("hi");
    }
}
