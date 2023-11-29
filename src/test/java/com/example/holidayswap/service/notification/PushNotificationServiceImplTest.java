package com.example.holidayswap.service.notification;

import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.notification.NotificationResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.notification.Notification;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.notification.NotificationRepository;
import com.example.holidayswap.utils.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PushNotificationServiceImplTest {

    @Mock
    private NotificationRepository mockNotificationRepository;
    @Mock
    private AuthUtils mockAuthUtils;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private SimpMessagingTemplate mockMessagingTemplate;

    private PushNotificationServiceImpl pushNotificationServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        pushNotificationServiceImplUnderTest = new PushNotificationServiceImpl(mockNotificationRepository,
                mockAuthUtils, mockUserRepository, mockMessagingTemplate);
    }

    @Test
    void testGetAllNotificationsByCurrentUser() {
        // Setup
        final List<NotificationResponse> expectedResult = List.of(NotificationResponse.builder().isRead(false).build());
        when(mockAuthUtils.getAuthenticatedUser()).thenReturn(User.builder().build());

        // Configure NotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(...).
        final List<Notification> notifications = List.of(Notification.builder()
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build());
        when(mockNotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false,
                User.builder().build())).thenReturn(notifications);

        // Run the test
        final List<NotificationResponse> result = pushNotificationServiceImplUnderTest.GetAllNotificationsByCurrentUser();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllNotificationsByCurrentUser_NotificationRepositoryReturnsNoItems() {
        // Setup
        when(mockAuthUtils.getAuthenticatedUser()).thenReturn(User.builder().build());
        when(mockNotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false,
                User.builder().build())).thenReturn(Collections.emptyList());

        // Run the test
        final List<NotificationResponse> result = pushNotificationServiceImplUnderTest.GetAllNotificationsByCurrentUser();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testSendNotificationToUser() {
        // Setup
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setSubject("subject");
        notificationRequest.setContent("content");
        notificationRequest.setHref("href");
        notificationRequest.setToUserId(0L);

        final NotificationResponse expectedResult = NotificationResponse.builder()
                .subject("subject")
                .content("content")
                .href("href")
                .isRead(false).build();
        when(mockUserRepository.findById(0L)).thenReturn(Optional.of(User.builder().build()));

        // Configure NotificationRepository.save(...).
        final Notification notification = Notification.builder()
                .subject("subject")
                .content("content")
                .href("href")
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build();
        when(mockNotificationRepository.save(Notification.builder()
                .subject("subject")
                .content("content")
                .href("href")
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build())).thenReturn(notification);

        // Run the test
        final NotificationResponse result = pushNotificationServiceImplUnderTest.SendNotificationToUser(
                notificationRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSendNotificationToUser_UserRepositoryReturnsAbsent() {
        // Setup
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setSubject("subject");
        notificationRequest.setContent("content");
        notificationRequest.setHref("href");
        notificationRequest.setToUserId(0L);

        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> pushNotificationServiceImplUnderTest.SendNotificationToUser(notificationRequest))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testMarkNotificationAsReadByNotificationId() {
        // Setup
        // Configure NotificationRepository.findById(...).
        final Optional<Notification> notification = Optional.of(Notification.builder()
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build());
        when(mockNotificationRepository.findById(0L)).thenReturn(notification);

        // Run the test
        pushNotificationServiceImplUnderTest.MarkNotificationAsReadByNotificationId(0L);

        // Verify the results
        mockNotificationRepository.findById(0L).ifPresent(item -> assertTrue(item::getIsRead));
    }

    @Test
    void testMarkNotificationAsReadByNotificationId_NotificationRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockNotificationRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> pushNotificationServiceImplUnderTest.MarkNotificationAsReadByNotificationId(0L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testMarkAllNotificationsAsReadByCurrentUser() {
        // Setup
        when(mockAuthUtils.getAuthenticatedUser()).thenReturn(User.builder().build());

        // Configure NotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(...).
        final List<Notification> notifications = List.of(Notification.builder()
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build());
        when(mockNotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false,
                User.builder().build())).thenReturn(notifications);

        // Run the test
        pushNotificationServiceImplUnderTest.MarkAllNotificationsAsReadByCurrentUser();

        // Verify the results
        notifications.forEach(notification -> {
            assertTrue(notification.getIsRead());
        });
    }

    @Test
    void testMarkAllNotificationsAsReadByCurrentUser_NotificationRepositoryFindAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDescReturnsNoItems() {
        // Setup
        when(mockAuthUtils.getAuthenticatedUser()).thenReturn(User.builder().build());
        when(mockNotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false,
                User.builder().build())).thenReturn(Collections.emptyList());

        // Run the test
        pushNotificationServiceImplUnderTest.MarkAllNotificationsAsReadByCurrentUser();

        // Verify the results
        verify(mockNotificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void testDeleteNotificationByNotificationId() {
        // Setup
        // Configure NotificationRepository.findById(...).
        final Optional<Notification> notification = Optional.of(Notification.builder()
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build());
        when(mockNotificationRepository.findById(0L)).thenReturn(notification);

        // Run the test
        pushNotificationServiceImplUnderTest.DeleteNotificationByNotificationId(0L);

        // Verify the results
        mockNotificationRepository.findById(0L).ifPresent(item -> assertTrue(item::getIsDeleted));
    }

    @Test
    void testDeleteNotificationByNotificationId_NotificationRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockNotificationRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> pushNotificationServiceImplUnderTest.DeleteNotificationByNotificationId(0L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testDeleteAllNotificationsByCurrentUser() {
        // Setup
        User authenticatedUser = User.builder().build();
        when(mockAuthUtils.getAuthenticatedUser()).thenReturn(authenticatedUser);

        // Create a list of notifications
        List<Notification> notifications = List.of(
                Notification.builder().isRead(false).isDeleted(false).user(authenticatedUser).build(),
                Notification.builder().isRead(false).isDeleted(false).user(authenticatedUser).build()
        );

        // Configure NotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(...)
        when(mockNotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false, authenticatedUser))
                .thenReturn(notifications);

        // Run the test
        pushNotificationServiceImplUnderTest.DeleteAllNotificationsByCurrentUser();

        // Verify the results
        verify(mockNotificationRepository, times(2)).save(any(Notification.class));
        notifications.forEach(notification -> {
            assertTrue(notification.getIsDeleted());
        });
    }

    @Test
    void testDeleteAllNotificationsByCurrentUser_NotificationRepositoryFindAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDescReturnsNoItems() {
        // Setup
        when(mockAuthUtils.getAuthenticatedUser()).thenReturn(User.builder().build());
        when(mockNotificationRepository.findAllByIsDeletedEqualsAndUserEqualsOrderByCreatedOnDesc(false,
                User.builder().build())).thenReturn(Collections.emptyList());

        // Run the test
        pushNotificationServiceImplUnderTest.DeleteAllNotificationsByCurrentUser();

        // Verify the results
        verify(mockNotificationRepository, times(0)).delete(any(Notification.class));
        verify(mockNotificationRepository, times(0)).save(any(Notification.class));
    }

    @Test
    void testCreateNotification() {
        // Setup
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setSubject("subject");
        notificationRequest.setContent("content");
        notificationRequest.setHref("href");
        notificationRequest.setToUserId(0L);

        when(mockUserRepository.findById(0L)).thenReturn(Optional.of(User.builder().build()));

        // Configure NotificationRepository.save(...).
        final Notification notification = Notification.builder()
                .subject("subject")
                .content("content")
                .href("href")
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build();
        when(mockNotificationRepository.save(Notification.builder()
                .subject("subject")
                .content("content")
                .href("href")
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build())).thenReturn(notification);

        // Run the test
        pushNotificationServiceImplUnderTest.CreateNotification(notificationRequest);

        // Verify the results
        verify(mockMessagingTemplate).convertAndSend("/topic/notification-0"
                , NotificationResponse.builder()
                        .subject("subject")
                        .content("content")
                        .href("href")
                        .isRead(false)
                        .build());
    }

    @Test
    void testCreateNotification_UserRepositoryReturnsAbsent() {
        // Setup
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setSubject("subject");
        notificationRequest.setContent("content");
        notificationRequest.setHref("href");
        notificationRequest.setToUserId(0L);

        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> pushNotificationServiceImplUnderTest.CreateNotification(notificationRequest))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testCreateNotification_SimpMessagingTemplateThrowsMessagingException() {
        // Setup
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setSubject("subject");
        notificationRequest.setContent("content");
        notificationRequest.setHref("href");
        notificationRequest.setToUserId(0L);

        when(mockUserRepository.findById(0L)).thenReturn(Optional.of(User.builder().build()));

        // Configure NotificationRepository.save(...).
        final Notification notification = Notification.builder()
                .subject("subject")
                .content("content")
                .href("href")
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build();
        when(mockNotificationRepository.save(Notification.builder()
                .subject("subject")
                .content("content")
                .href("href")
                .isRead(false)
                .isDeleted(false)
                .user(User.builder().build())
                .build())).thenReturn(notification);

        doThrow(MessagingException.class).when(mockMessagingTemplate).convertAndSend("/topic/notification-0"
                , NotificationResponse.builder()
                        .subject("subject")
                        .content("content")
                        .href("href")
                        .isRead(false)
                        .build());

        // Run the test
        assertThatThrownBy(
                () -> pushNotificationServiceImplUnderTest.CreateNotification(notificationRequest))
                .isInstanceOf(MessagingException.class);
    }
}
