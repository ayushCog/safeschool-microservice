package com.cognizant.resolutionservice.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resolutions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resolutionId;

    @NotNull(message = "Incident ID is required")
    private Long incidentId;

    @NotNull(message = "Resolving officer ID is required")
    private Long officerId;

    @NotBlank(message = "Action details are required")
    @Column(columnDefinition = "TEXT")
    private String actions;

    @NotNull(message = "Resolution date is required")
    private LocalDate date;

    @NotBlank(message = "Resolution status is required")
    private String status;
}