package com.cognizant.trainingservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ProgramProjection {
    private Long programId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
