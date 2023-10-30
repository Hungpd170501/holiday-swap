package com.example.holidayswap.controller.notification;

import com.example.holidayswap.domain.dto.request.auth.RoleRequest;
import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.auth.RoleResponse;
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
        return ResponseEntity.ok(pushNotificationService.GetAllNotificationsByCurrentUser());
    }

    @PostMapping
    public void createNotification(@RequestBody NotificationRequest notificationRequest) {
        var user = authUtils.getAuthenticatedUser();
        var notification = pushNotificationService.SendNotificationToUser(notificationRequest);
        messagingTemplate.convertAndSend("/topic/notification-" + user.getUserId(), notification);
    }

    @DeleteMapping("/current-user/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("notificationId") Long notificationId) {
        pushNotificationService.DeleteNotificationByNotificationId(notificationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/current-user")
    public ResponseEntity<Void> deleteAllNotificationsByCurrentUser() {
        pushNotificationService.DeleteAllNotificationsByCurrentUser();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/current-user/read-all")
    public ResponseEntity<Void> markAllNotificationsAsReadByCurrentUser() {
        pushNotificationService.MarkAllNotificationsAsReadByCurrentUser();
        return ResponseEntity.noContent().build();
    }
}
