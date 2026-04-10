package com.cognizant.incidentservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class IncidentProjection {
    private Long incidentId;
    private Long reporter;
    private String type;
    private String location;
    private LocalDate date;
    private String status;
}
