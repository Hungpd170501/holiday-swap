package com.example.holidayswap.repository.firebaseNotification;

import com.example.holidayswap.domain.entity.notification.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationUserRepository extends JpaRepository<NotificationUser,Long> {
    List<NotificationUser> findAllByUserIdOrderByIdDesc(Long userId);

    List<NotificationUser> findAllByUserIdAndIsRead(Long userId, Boolean isRead);
}
