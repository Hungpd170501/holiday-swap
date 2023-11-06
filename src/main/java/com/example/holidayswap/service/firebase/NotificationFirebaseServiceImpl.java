package com.example.holidayswap.service.firebase;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.notification.NotificationFirebase;
import com.example.holidayswap.repository.firebaseNotification.NotificationFirebaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationFirebaseServiceImpl implements INotificationFirebaseService {

    private final NotificationFirebaseRepository notificationFirebaseRepository;

    @Override
    public List<NotificationFirebase> GetAllNotificationFirebaseByUserId(Long userId) {
        return notificationFirebaseRepository.findAllByUserId(userId);
    }

    @Override
    public void CreateNotificationFirebaseByUserLogin(String fcmToken) {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        var notiCheck = notificationFirebaseRepository.findByFcmTokenAndAndUserId(fcmToken, user.getUserId());
        if (notiCheck != null) {
            return;
        }

        NotificationFirebase notificationFirebase = new NotificationFirebase();
        notificationFirebase.setUserId(user.getUserId());
        notificationFirebase.setFcmToken(fcmToken);
        notificationFirebaseRepository.save(notificationFirebase);
    }

    @Override
    public void DeleteNotificationFirebaseByUserLoginAndFCMToken(String fcmToken) {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        var notiCheck = notificationFirebaseRepository.findByFcmTokenAndAndUserId(fcmToken, user.getUserId());
        if (notiCheck == null) {
            return;
        }
        notificationFirebaseRepository.delete(notiCheck);
    }
}
