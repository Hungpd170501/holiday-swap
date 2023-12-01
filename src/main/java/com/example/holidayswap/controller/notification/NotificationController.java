package com.example.holidayswap.controller.notification;

import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.notification.NotificationResponse;
import com.example.holidayswap.service.notification.PushNotificationService;
import com.example.holidayswap.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;
    private final PushNotificationService pushNotificationService;
    private final AuthUtils authUtils;

    @GetMapping("/current-user")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        return ResponseEntity.ok(pushNotificationService.getAllNotificationsByCurrentUser());
    }

    @PostMapping
    public void createNotification(@RequestBody NotificationRequest notificationRequest) {
        var notification = pushNotificationService.sendNotificationToUser(notificationRequest);
        messagingTemplate.convertAndSend("/topic/notification-" + notificationRequest.getToUserId().toString(), notification);
    }

    @DeleteMapping("/current-user/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("notificationId") Long notificationId) {
        pushNotificationService.deleteNotificationByNotificationId(notificationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/current-user")
    public ResponseEntity<Void> deleteAllNotificationsByCurrentUser() {
        pushNotificationService.deleteAllNotificationsByCurrentUser();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/current-user/read-all")
    public ResponseEntity<Void> markAllNotificationsAsReadByCurrentUser() {
        pushNotificationService.markAllNotificationsAsReadByCurrentUser();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/current-user/read/{notificationId}")
    public ResponseEntity<Void> markNotificationAsReadByCurrentUser(@PathVariable("notificationId") Long notificationId) {
        pushNotificationService.markCurrentUserNotificationAsReadByNotificationId(notificationId);
        return ResponseEntity.noContent().build();
    }
}
