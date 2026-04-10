package com.cognizant.incidentservice.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incidentId;

    @NotNull(message = "Reporter is required")
    private Long reporterId;

    @NotBlank(message = "Incident type is required")
    private String type;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Incident date is required")
    @PastOrPresent(message = "Incident date cannot be in the future")
    private LocalDate date;

    @NotBlank(message = "Status is required")
    private String status;

    private Long resolutionId;
}
