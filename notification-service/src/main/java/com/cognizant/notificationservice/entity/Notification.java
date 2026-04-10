package com.cognizant.notificationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @NotNull(message = "Notification must be assigned to a user")
    private Long userId;

    @NotNull(message = "Entity ID is required")
    private Long entityId;

    @NotBlank(message = "Notification message cannot be empty")
    @Size(max = 500)
    private String message;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Created date is required")
    @PastOrPresent
    private LocalDateTime createdDate;
}
