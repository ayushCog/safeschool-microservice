package com.cognizant.incidentservice.service;

import com.cognizant.incidentservice.dto.IncidentDto;
import com.cognizant.incidentservice.projection.IncidentProjection;
import com.cognizant.incidentservice.projection.SuccessResponseProjection;

import java.util.List;

public interface IIncidentService {
    public SuccessResponseProjection<List<IncidentProjection>> getAllIncidents();
    public SuccessResponseProjection<List<IncidentProjection>> getUserIncidents(Long userId);
    public SuccessResponseProjection<IncidentProjection> createIncident(IncidentDto incidentDto);
    public SuccessResponseProjection<Boolean> checkIncidentExists(Long id);
    public SuccessResponseProjection<String> updateIncidentStatus(Long id, String status);
}
