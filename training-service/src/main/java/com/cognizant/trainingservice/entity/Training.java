package com.cognizant.trainingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingId;

    @NotNull(message = "Program ID is required")
    private Long programId;

    @NotNull(message = "Staff ID is required")
    private Long userId;

    private LocalDate completionDate;

    @NotBlank(message = "Training status is required")
    private String status;
}
