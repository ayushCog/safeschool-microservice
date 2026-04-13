package com.cognizant.complianceservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComplianceRecordDto {
    @NotNull(message = "Entity ID is required")
    private Long entityId;

    @NotBlank(message = "Compliance type is required")
    private String type;

    @NotBlank(message = "Result is required (e.g., PASSED, FAILED, PENDING)")
    private String result;

    private String notes;

    @NotNull(message = "Officer ID is required for accountability")
    private Long officerId;

    @NotNull(message = "Compliance date is required")
    @PastOrPresent(message = "Compliance date cannot be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;
}
