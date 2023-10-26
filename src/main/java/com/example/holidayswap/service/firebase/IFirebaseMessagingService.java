package com.example.holidayswap.service.firebase;

import com.example.holidayswap.domain.dto.response.firebase.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessagingException;

public interface IFirebaseMessagingService {
    String sendNotificationByToken(NotificationMessage notificationMessage) throws FirebaseMessagingException;

    default void hi() {
        System.out.println("hi");
    }
}
