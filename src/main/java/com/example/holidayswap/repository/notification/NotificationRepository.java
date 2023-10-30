package com.example.holidayswap.repository.notification;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByIsDeletedEqualsAndUserEquals(boolean isDeleted, User user);
}
