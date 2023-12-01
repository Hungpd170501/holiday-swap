package com.example.holidayswap.service.notification;

import com.example.holidayswap.constants.ErrorMessage;
import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.notification.NotificationResponse;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
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
    public List<NotificationResponse> getAllNotificationsByCurrentUser() {
        var user = authUtils.getAuthenticatedUser();
        return notificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false, user).stream()
                .map(NotificationMapper.INSTANCE::toNotificationResponse)
                .toList();
    }

    @Override
    public NotificationResponse sendNotificationToUser(NotificationRequest notificationRequest) {
        var notification = NotificationMapper.INSTANCE.toNotification(notificationRequest);
        userRepository.findById(notificationRequest.getToUserId()).ifPresentOrElse(
                notification::setUser,
                () -> {
                    throw new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND);
                });
        notification.setIsRead(false);
        notification.setIsDeleted(false);
        return NotificationMapper.INSTANCE.toNotificationResponse(notificationRepository.save(notification));
    }

    @Override
    public void markNotificationAsReadByNotificationId(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresentOrElse(
                notification -> {
                    notification.setIsRead(true);
                    notificationRepository.save(notification);
                },
                () -> {
                    throw new EntityNotFoundException(ErrorMessage.NOTIFICATION_NOT_FOUND);
                }
        );
    }

    @Override
    public void markAllNotificationsAsReadByCurrentUser() {
        var user = authUtils.getAuthenticatedUser();
        notificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false, user).forEach(
                notification -> {
                    notification.setIsRead(true);
                    notificationRepository.save(notification);
                }
        );
    }

    @Override
    public void deleteNotificationByNotificationId(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresentOrElse(
                notification -> {
                    notification.setIsDeleted(true);
                    notificationRepository.save(notification);
                },
                () -> {
                    throw new EntityNotFoundException(ErrorMessage.NOTIFICATION_NOT_FOUND);
                }
        );
    }

    @Override
    public void deleteAllNotificationsByCurrentUser() {
        var user = authUtils.getAuthenticatedUser();
        notificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false, user).forEach(
                notification -> {
                    notification.setIsDeleted(true);
                    notificationRepository.save(notification);
                }
        );
    }

    @Override
    public void createNotification(NotificationRequest notificationRequest) {
        var notification = sendNotificationToUser(notificationRequest);
        messagingTemplate.convertAndSend("/topic/notification-" + notificationRequest.getToUserId(), notification);
    }

    @Override
    public void markCurrentUserNotificationAsReadByNotificationId(Long notificationId) {
        var user = authUtils.getAuthenticatedUser();
        notificationRepository.findById(notificationId).ifPresentOrElse(
                notification -> {
                    if (notification.getUser().getUserId().equals(user.getUserId())) {
                        notification.setIsRead(true);
                        notificationRepository.save(notification);
                    }
                },
                () -> {
                    throw new EntityNotFoundException(ErrorMessage.NOTIFICATION_NOT_FOUND);
                }
        );
    }
}
