package com.cognizant.notificationservice.controller;

import com.cognizant.notificationservice.dto.NotificationDto;
import com.cognizant.notificationservice.projection.NotificationProjection;
import com.cognizant.notificationservice.projection.SuccessResponseProjection;
import com.cognizant.notificationservice.service.INotificationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@Slf4j
public class NotificationController {
    @Autowired
    INotificationService notificationServiceImpl;

    @GetMapping("/allnotifications/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<NotificationProjection>>> getNotifications(@PathVariable Long userId) {
        log.info("Received GET request: Fetching all notifications for User ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(notificationServiceImpl.getNotifications(userId));
    }

    @PatchMapping("/mark-read/{userId}/{notificationId}")
    public ResponseEntity<SuccessResponseProjection<String>> markAsRead(@PathVariable Long userId, @PathVariable Long notificationId) {
        log.info("Received PATCH request: User ID: {} marking Notification ID: {} as READ", userId, notificationId);
        return ResponseEntity.status(HttpStatus.OK).body(notificationServiceImpl.markAsRead(userId, notificationId));
    }

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @PostMapping("/broadcast")
    public ResponseEntity<SuccessResponseProjection<String>> broadcastAlert(@Valid @RequestBody NotificationDto notificationDto) {
        log.info("Received POST request: Broadcast initiated. Category: {}", notificationDto.getCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationServiceImpl.broadcastAlert(notificationDto));
    }

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @PostMapping("/role/{role}")
    public ResponseEntity<SuccessResponseProjection<String>> sendGroupAlert(@PathVariable String role, @Valid @RequestBody NotificationDto notificationDto) {
        log.info("Received POST request: Role-based alert for Role: {} initiated. Category: {}", role, notificationDto.getCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationServiceImpl.sendGroupAlert(role, notificationDto));
    }
}
