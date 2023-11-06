package com.example.holidayswap.service.firebase;

import com.example.holidayswap.domain.dto.response.firebase.NotificationMessage;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingServiceImpl implements IFirebaseMessagingService{

//    @Autowired
//    private FirebaseMessaging firebaseMessaging;
    @Override
    public String sendNotificationByToken(NotificationMessage notificationMessage) {
//        Notification notification = Notification.builder()
//                .setTitle(notificationMessage.getTitle())
//                .setBody(notificationMessage.getBody())
//                .setImage(notificationMessage.getImage())
//                .build();
//        Message message = Message.builder()
//                .setToken(notificationMessage.getRecipientToken())
//                .setNotification(notification)
//                .putAllData(notificationMessage.getData())
//                .build();
//
//        try{
//            firebaseMessaging.send(message);
//            return "Notification sent successfully";
//        }catch (FirebaseMessagingException ex){
//            ex.printStackTrace();
            return "Notification could not be sent";
//        }
    }
}
