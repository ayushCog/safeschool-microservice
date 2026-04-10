package com.cognizant.trainingservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class TrainingProjection {
    private Long trainingId;
    private Long programId;
    private Long userId;
    private LocalDate completionDate;
    private String status;
}
