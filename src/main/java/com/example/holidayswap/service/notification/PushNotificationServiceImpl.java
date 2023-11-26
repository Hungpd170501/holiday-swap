package com.example.holidayswap.service.notification;

import com.example.holidayswap.constants.ErrorMessage;
import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.notification.NotificationResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.mapper.notification.NotificationMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.notification.NotificationRepository;
import com.example.holidayswap.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {
    private final NotificationRepository notificationRepository;
    private final AuthUtils authUtils;
    private final UserRepository userRepository;

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<NotificationResponse> GetAllNotificationsByCurrentUser() {
        var user = authUtils.getAuthenticatedUser();
        return notificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false, user).stream()
                .map(NotificationMapper.INSTANCE::toNotificationResponse)
                .toList();
    }

    @Override
    public NotificationResponse SendNotificationToUser(NotificationRequest notificationRequest) {
        var notification = NotificationMapper.INSTANCE.toNotification(notificationRequest);
        userRepository.findById(notificationRequest.getToUserId()).ifPresentOrElse(
                notification::setUser,
                () -> {
                    throw new RuntimeException(ErrorMessage.USER_NOT_FOUND);
                });
        notification.setIsRead(false);
        notification.setIsDeleted(false);
        return NotificationMapper.INSTANCE.toNotificationResponse(notificationRepository.save(notification));
    }

    @Override
    public void MarkNotificationAsReadByNotificationId(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresentOrElse(
                notification -> {
                    notification.setIsRead(true);
                    notificationRepository.save(notification);
                },
                () -> {
                    throw new RuntimeException(ErrorMessage.NOTIFICATION_NOT_FOUND);
                }
        );
    }

    @Override
    public void MarkAllNotificationsAsReadByCurrentUser() {
        var user = authUtils.getAuthenticatedUser();
        notificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false, user).forEach(
                notification -> {
                    notification.setIsRead(true);
                    notificationRepository.save(notification);
                }
        );
    }

    @Override
    public void DeleteNotificationByNotificationId(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresentOrElse(
                notification -> {
                    notification.setIsDeleted(true);
                    notificationRepository.save(notification);
                },
                () -> {
                    throw new RuntimeException(ErrorMessage.NOTIFICATION_NOT_FOUND);
                }
        );
    }

    @Override
    public void DeleteAllNotificationsByCurrentUser() {
        var user = authUtils.getAuthenticatedUser();
        notificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false, user).forEach(
                notification -> {
                    notification.setIsDeleted(true);
                    notificationRepository.save(notification);
                }
        );
    }

    @Override
    public void CreateNotification(NotificationRequest notificationRequest) {
        var notification = SendNotificationToUser(notificationRequest);
        messagingTemplate.convertAndSend("/topic/notification-" + notificationRequest.getToUserId(), notification);
    }
}
