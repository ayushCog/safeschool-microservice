package com.cognizant.notificationservice.service;

import com.cognizant.notificationservice.proxy.UserProxy;
import com.cognizant.notificationservice.classexception.NotificationException;
import com.cognizant.notificationservice.dto.NotificationDto;
import com.cognizant.notificationservice.entity.Notification;
import com.cognizant.notificationservice.projection.NotificationProjection;
import com.cognizant.notificationservice.projection.SuccessResponseProjection;
import com.cognizant.notificationservice.repository.NotificationRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserProxy userProxy;

    public SuccessResponseProjection<List<NotificationProjection>> getNotifications(Long userId) {
        log.info("Service request: Retrieving notification list for User ID: {}", userId);
        List<NotificationProjection> notifications = notificationRepository.findAllByUserId(userId);
        log.info("Found {} notifications for User ID: {}", notifications.size(), userId);
        return new SuccessResponseProjection<>(true, "Notifications retrieved", notifications);
    }

    @Transactional
    public SuccessResponseProjection<String> markAsRead(Long userId, Long notificationId) {
        log.info("Service request: Attempting to update status for Notification ID: {}", notificationId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException("Notification not found", HttpStatus.NOT_FOUND));

        if (!notification.getUserId().equals(userId)) {
            log.error("Alert: User ID: {} unauthorized access to Notification ID: {}", userId, notificationId);
            throw new NotificationException("Unauthorized access to notification", HttpStatus.UNAUTHORIZED);
        }

        notification.setStatus("READ");
        notificationRepository.save(notification);

        return new SuccessResponseProjection<>(true, "Notification marked as read", "READ");
    }

    @Transactional
    @CircuitBreaker(name="getUser")
    public SuccessResponseProjection<String> broadcastAlert(NotificationDto notificationDto) {
        log.info("Service request: Processing global broadcast. Category: {}", notificationDto.getCategory());

        List<Long> allUserIds = userProxy.getAllUserIds().getBody().getData();
        log.info("Found {} total users to receive global broadcast", allUserIds.size());

        List<Notification> alerts = allUserIds.stream().map(userId -> {
            Notification n = new Notification();
            n.setUserId(userId);
            n.setEntityId(notificationDto.getEntityId());
            n.setMessage(notificationDto.getMessage());
            n.setCategory(notificationDto.getCategory());
            n.setStatus("UNREAD");
            n.setCreatedDate(LocalDateTime.now());
            return n;
        }).collect(Collectors.toList());

        notificationRepository.saveAll(alerts);
        return new SuccessResponseProjection<>(true, "Alert broadcasted to all users", notificationDto.getMessage());
    }

    @Transactional
    @CircuitBreaker(name="getUser")
    public SuccessResponseProjection<String> sendGroupAlert(String role, NotificationDto notificationDto) {
        log.info("Service request: Processing role-based alert for Group: {}", role);

        List<Long> groupUserIds = userProxy.getUserIdsByRole(role).getBody().getData();

        if (notificationDto.getEntityId() == null) notificationDto.setEntityId(1L);
        if (notificationDto.getCategory() == null) notificationDto.setCategory("GENERAL");

        List<Notification> alerts = groupUserIds.stream().map(userId -> {
            Notification n = new Notification();
            n.setUserId(userId);
            n.setEntityId(notificationDto.getEntityId());
            n.setMessage(notificationDto.getMessage());
            n.setCategory(notificationDto.getCategory());
            n.setStatus("UNREAD");
            n.setCreatedDate(LocalDateTime.now());
            return n;
        }).collect(Collectors.toList());

        notificationRepository.saveAll(alerts);
        log.info("Role-based alert completed. Sent to {} users with role {}", alerts.size(), role);

        return new SuccessResponseProjection<>(true, "Role-based alert sent", notificationDto.getMessage());
    }
}