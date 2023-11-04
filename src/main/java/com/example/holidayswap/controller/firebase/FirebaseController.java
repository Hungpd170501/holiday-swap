package com.example.holidayswap.controller.firebase;

import com.example.holidayswap.domain.dto.response.firebase.NotificationMessage;
import com.example.holidayswap.domain.entity.notification.NotificationUser;
import com.example.holidayswap.service.firebase.IFirebaseMessagingService;
import com.example.holidayswap.service.firebase.INotificationFirebaseService;
import com.example.holidayswap.service.firebase.INotificationUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/firebase")
@AllArgsConstructor
public class FirebaseController {

    @Autowired
    private final IFirebaseMessagingService firebaseMessagingService;


    private final INotificationFirebaseService notificationFirebaseService;

    private final INotificationUserService notificationUserService;

    @PostMapping("/send")
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage)  {
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }

    @PostMapping("/sendtokenfcm")
    public void storeTokenFCM(@RequestBody String tokenFCM) {
        notificationFirebaseService.CreateNotificationFirebaseByUserLogin(tokenFCM);
    }

    @DeleteMapping("/deletetokenfcm")
    public void deleteTokenFCM(@RequestBody String tokenFCM) {
        notificationFirebaseService.DeleteNotificationFirebaseByUserLoginAndFCMToken(tokenFCM);
    }

    @GetMapping("/getnotification")
    public ResponseEntity<List<NotificationUser>> getNotification() {
        var listNoti = notificationUserService.GetAllNotificationByUserLogin();
        return listNoti != null ? ResponseEntity.ok(listNoti) : ResponseEntity.badRequest().body(null);
    }

    @PatchMapping("/readnotification")
    public void readNotification() {
        notificationUserService.IsReadNotificationByUserLogin();
    }

    @GetMapping("/countnotification")
    public ResponseEntity<Integer> countNotification() {
        var countNoti = notificationUserService.CountNotificationNotReadByUserLogin();
        return countNoti != 0 ? ResponseEntity.ok(countNoti) : ResponseEntity.badRequest().body(0);
    }
}
