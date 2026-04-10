package com.cognizant.incidentservice.service;

import com.cognizant.incidentservice.classexception.IncidentException;
import com.cognizant.incidentservice.dto.IncidentDto;
import com.cognizant.incidentservice.entity.Incident;
import com.cognizant.incidentservice.projection.IncidentProjection;
import com.cognizant.incidentservice.projection.SuccessResponseProjection;
import com.cognizant.incidentservice.proxy.UserProxy;
import com.cognizant.incidentservice.repository.IncidentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class IncidentServiceImpl implements IIncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserProxy userProxy;

    public SuccessResponseProjection<List<IncidentProjection>> getAllIncidents() {
        log.info("Service request: Retrieving full incident list");
        List<IncidentProjection> incidents = incidentRepository.getAllIncidents();
        return new SuccessResponseProjection<>(true, "Incidents retrieved successfully", incidents);
    }

    public SuccessResponseProjection<List<IncidentProjection>> getUserIncidents(Long userId) {
        log.info("Service request: Fetching incident history for User ID: {}", userId);
        List<IncidentProjection> incidents = incidentRepository.getUserIncidents(userId);

        if (incidents.isEmpty()) {
            throw new IncidentException("No user incidents found", HttpStatus.NOT_FOUND);
        }
        return new SuccessResponseProjection<>(true, "User incidents retrieved successfully", incidents);
    }

    @Transactional
    @CircuitBreaker(name="checkUser")
    public SuccessResponseProjection<IncidentProjection> createIncident(IncidentDto incidentDto) {
        log.info("Service request: Initiating incident creation for User ID: {}", incidentDto.getUserId());

        try {
            Boolean userExists = userProxy.checkUserExists(incidentDto.getUserId()).getBody().getData();

            if (userExists == null || !userExists) {
                throw new IncidentException("User not found in User Service", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Failed to communicate with User Service: {}", e.getMessage());
            throw new IncidentException("User validation failed", HttpStatus.SERVICE_UNAVAILABLE);
        }

        Incident incident = new Incident();
        incident.setType(incidentDto.getType());
        incident.setLocation(incidentDto.getLocation());
        incident.setDate(incidentDto.getDate() != null ? incidentDto.getDate() : LocalDate.now());
        incident.setStatus(incidentDto.getStatus());
        incident.setReporterId(incidentDto.getUserId());

        Incident savedIncident = incidentRepository.save(incident);

        IncidentProjection incidentProjection = new IncidentProjection(
                savedIncident.getIncidentId(),
                savedIncident.getReporterId(),
                savedIncident.getType(),
                savedIncident.getLocation(),
                savedIncident.getDate(),
                savedIncident.getStatus()
        );

        log.info("Successfully saved Incident ID: {}", savedIncident.getIncidentId());
        return new SuccessResponseProjection<>(true, "Incident reported successfully", incidentProjection);
    }

    public SuccessResponseProjection<Boolean> checkIncidentExists(Long id) {
        log.info("Service: Checking existence of Incident ID: {}", id);
        boolean exists = incidentRepository.existsById(id);
        log.info("Service: Incident ID {} existence status: {}", id, exists);
        return new SuccessResponseProjection<>(true, "Incident existence check completed", exists);
    }

    @Transactional
    public SuccessResponseProjection<String> updateIncidentStatus(Long id, String status) {
        log.info("Service: Attempting to update status for Incident ID: {}", id);

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service: Update failed. Incident ID {} not found", id);
                    return new IncidentException("Incident not found with id: " + id, HttpStatus.NOT_FOUND);
                });

        String oldStatus = incident.getStatus();
        incident.setStatus(status);
        incidentRepository.save(incident);

        log.info("Service: Incident ID {} status changed from {} to {}", id, oldStatus, status);
        return new SuccessResponseProjection<>(true, "Incident status updated successfully", status);
    }
}