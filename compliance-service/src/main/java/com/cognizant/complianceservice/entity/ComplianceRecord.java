package com.cognizant.complianceservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complianceId;

    @NotNull(message = "Entity ID cannot be null")
    @Column(nullable = false)
    private Long entityId;

    @NotBlank(message = "Type is mandatory")
    @Column(nullable = false)
    private String type;

    @NotBlank(message = "Result is mandatory")
    @Column(nullable = false)
    private String result;

    @NotNull(message = "Date is mandatory")
    @PastOrPresent(message = "Compliance date cannot be in the future")
    @Column(nullable = false)
    private LocalDate date;

    private String notes;
}
