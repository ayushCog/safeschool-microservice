package com.cognizant.incidentservice.dto;

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
public class IncidentDto {
    @NotNull(message = "Reporter User ID is required")
    private Long userId;

    @NotBlank(message = "Incident type is required")
    private String type;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Incident date cannot be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    private String status = "REPORTED";
}
