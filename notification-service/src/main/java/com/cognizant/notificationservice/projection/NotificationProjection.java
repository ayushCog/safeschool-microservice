package com.cognizant.notificationservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class NotificationProjection {
    private Long notificationId;
    private Long userId;
    private Long entityId;
    private String message;
    private String category;
    private String status;
    private LocalDateTime createdDate;
}
