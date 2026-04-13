package com.cognizant.complianceservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ComplianceProjection {
    private Long complianceId;
    private Long entityId;
    private String type;
    private String result;
    private LocalDate date;
    private String notes;
}
