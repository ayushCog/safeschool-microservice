package com.cognizant.resolutionservice.controller;

import com.cognizant.resolutionservice.dto.ResolutionDto;
import com.cognizant.resolutionservice.projection.ResolutionProjection;
import com.cognizant.resolutionservice.projection.SuccessResponseProjection;
import com.cognizant.resolutionservice.service.IResolutionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resolution")
@Slf4j
public class ResolutionController {
    @Autowired
    private IResolutionService resolutionServiceImpl;

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<ResolutionProjection>> createResolution(@Valid @RequestBody ResolutionDto resolutionDto){
        log.info("Received POST request: Creating resolution for Incident ID: {} by Officer ID: {}", resolutionDto.getIncidentId(), resolutionDto.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(resolutionServiceImpl.createResolution(resolutionDto));
    }

    @GetMapping("/get/incident/{incidentId}")
    public ResponseEntity<SuccessResponseProjection<ResolutionProjection>> getResolutionByIncident(@PathVariable Long incidentId){
        log.info("Received GET request: Fetching resolution for Incident ID: {}", incidentId);
        return ResponseEntity.status(HttpStatus.OK).body(resolutionServiceImpl.getResolutionByIncident(incidentId));
    }

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @PutMapping("/update/{resolutionId}")
    public ResponseEntity<SuccessResponseProjection<ResolutionProjection>> updateResolution(@PathVariable Long resolutionId, @RequestBody ResolutionDto resolutionDto){
        log.info("Received PUT request: Updating Resolution ID: {}", resolutionId);
        return ResponseEntity.status(HttpStatus.OK).body(resolutionServiceImpl.updateResolution(resolutionId, resolutionDto));
    }

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @GetMapping("/get/user/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<ResolutionProjection>>> getUserResolution(@PathVariable Long userId){
        log.info("Received GET request: Fetching all resolutions handled by Officer ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(resolutionServiceImpl.getUserResolution(userId));
    }
}
