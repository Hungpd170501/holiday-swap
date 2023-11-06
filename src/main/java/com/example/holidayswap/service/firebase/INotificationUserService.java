package com.example.holidayswap.service.firebase;

import com.example.holidayswap.domain.entity.notification.NotificationEnum;
import com.example.holidayswap.domain.entity.notification.NotificationUser;

import java.util.List;

public interface INotificationUserService {
    List<NotificationUser> GetAllNotificationByUserLogin();

    void CreateNotificationByUserId(Long userId,String title,String content, String link);

    void IsReadNotificationByUserLogin();

    int CountNotificationNotReadByUserLogin();
}
