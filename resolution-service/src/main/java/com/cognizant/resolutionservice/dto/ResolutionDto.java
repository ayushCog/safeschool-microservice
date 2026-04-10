package com.cognizant.resolutionservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResolutionDto {
    @NotNull(message = "Incident ID is required")
    private Long incidentId;

    @NotNull(message = "Resolving Officer ID is required")
    private Long userId;

    @NotBlank(message = "Action details are required")
    private String actions;

    @NotNull(message = "Resolution date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    private String status;
}
