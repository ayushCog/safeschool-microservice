package com.cognizant.resolutionservice.proxy;

import com.cognizant.resolutionservice.config.FeignConfig;
import com.cognizant.resolutionservice.dto.NotificationDto;
import com.cognizant.resolutionservice.projection.SuccessResponseProjection;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "notification-service", configuration = FeignConfig.class)
public interface NotificationProxy {
    @PostMapping("/notification/create")
    public ResponseEntity<SuccessResponseProjection<String>> createNotification(@Valid @RequestBody NotificationDto notificationDto);
}