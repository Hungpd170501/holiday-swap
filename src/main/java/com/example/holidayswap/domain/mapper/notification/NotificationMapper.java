package com.example.holidayswap.domain.mapper.notification;

import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.notification.NotificationResponse;
import com.example.holidayswap.domain.entity.notification.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "userId", target = "user", ignore = true)
    Notification toNotification(NotificationRequest notificationRequest);

    NotificationResponse toNotificationResponse(Notification notification);
}
