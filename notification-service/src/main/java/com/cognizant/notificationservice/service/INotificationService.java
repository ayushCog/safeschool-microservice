package com.cognizant.notificationservice.service;

import com.cognizant.notificationservice.dto.NotificationDto;
import com.cognizant.notificationservice.projection.NotificationProjection;
import com.cognizant.notificationservice.projection.SuccessResponseProjection;

import java.util.List;

public interface INotificationService {
    SuccessResponseProjection<List<NotificationProjection>> getNotifications(Long userId);
    SuccessResponseProjection<String> markAsRead(Long userId, Long notificationId);
    SuccessResponseProjection<String> broadcastAlert(NotificationDto notificationDto);
    SuccessResponseProjection<String> sendGroupAlert(String role, NotificationDto notificationDto);
}
