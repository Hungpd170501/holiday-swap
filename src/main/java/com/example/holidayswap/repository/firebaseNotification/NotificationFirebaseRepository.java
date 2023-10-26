package com.example.holidayswap.repository.firebaseNotification;

import com.example.holidayswap.domain.entity.notification.NotificationFirebase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationFirebaseRepository extends JpaRepository<NotificationFirebase, Long> {
    List<NotificationFirebase> findAllByUserId(Long userId);

    NotificationFirebase findByFcmTokenAndAndUserId(String fcmToken, Long userId);
}
