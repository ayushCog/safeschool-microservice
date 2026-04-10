package com.cognizant.incidentservice.controller;

import com.cognizant.incidentservice.dto.IncidentDto;
import com.cognizant.incidentservice.projection.IncidentProjection;
import com.cognizant.incidentservice.projection.SuccessResponseProjection;
import com.cognizant.incidentservice.service.IIncidentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidents")
@Slf4j
public class IncidentController {
    @Autowired
    IIncidentService incidentServiceImpl;

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @GetMapping("/")
    public ResponseEntity<SuccessResponseProjection<List<IncidentProjection>>> getAllIncidents(){
        log.info("Received GET request: Fetching all incidents");
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.getAllIncidents());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<IncidentProjection>>> getUserIncidents(@PathVariable Long userId){
        log.info("Received GET request: Fetching incidents for User ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.getUserIncidents(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<IncidentProjection>> createIncident(@Valid @RequestBody IncidentDto incidentDto){
        log.info("Received POST request: Creating new incident report by User ID: {}", incidentDto.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.createIncident(incidentDto));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<SuccessResponseProjection<Boolean>> checkIncidentExists(@PathVariable("id") Long id) {
        log.info("Received GET request: Checking existence for Incident ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.checkIncidentExists(id));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<SuccessResponseProjection<String>> updateIncidentStatus(@PathVariable("id") Long id, String status) {
        log.info("Received PUT request: Updating Incident ID: {} to Status: {}", id, status);
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.updateIncidentStatus(id, status));
    }
}
