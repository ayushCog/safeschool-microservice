package com.cognizant.resolutionservice.service;

import com.cognizant.resolutionservice.proxy.IncidentProxy;
import com.cognizant.resolutionservice.proxy.UserProxy;
import com.cognizant.resolutionservice.classexception.ResolutionException;
import com.cognizant.resolutionservice.dto.ResolutionDto;
import com.cognizant.resolutionservice.entity.Resolution;
import com.cognizant.resolutionservice.projection.ResolutionProjection;
import com.cognizant.resolutionservice.projection.SuccessResponseProjection;
import com.cognizant.resolutionservice.repository.ResolutionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ResolutionServiceImpl implements IResolutionService {

    @Autowired
    private ResolutionRepository resolutionRepository;

    @Autowired
    private IncidentProxy incidentProxy;

    @Autowired
    private UserProxy userProxy;

    @Transactional
    @CircuitBreaker(name="checkIncident")
    public SuccessResponseProjection<ResolutionProjection> createResolution(ResolutionDto resolutionDto) {
        log.info("Service request: Initiating resolution for Incident ID: {}", resolutionDto.getIncidentId());

        ResponseEntity<SuccessResponseProjection<Boolean>> incidentRes = incidentProxy.checkIncidentExists(resolutionDto.getIncidentId());
        if (incidentRes.getBody() == null || !incidentRes.getBody().getData()) {
            log.error("Resolution failed: Incident ID {} not found", resolutionDto.getIncidentId());
            throw new ResolutionException("Incident not found", HttpStatus.NOT_FOUND);
        }

        ResponseEntity<SuccessResponseProjection<Boolean>> userRes = userProxy.checkUserExists(resolutionDto.getUserId());
        if (userRes.getBody() == null || !userRes.getBody().getData()) {
            log.error("Resolution failed: Officer ID {} not found", resolutionDto.getUserId());
            throw new ResolutionException("Officer not found", HttpStatus.NOT_FOUND);
        }

        Resolution resolution = new Resolution();
        resolution.setIncidentId(resolutionDto.getIncidentId());
        resolution.setOfficerId(resolutionDto.getUserId());
        resolution.setActions(resolutionDto.getActions());
        resolution.setDate(resolutionDto.getDate());
        resolution.setStatus(resolutionDto.getStatus());

        Resolution savedRes = resolutionRepository.save(resolution);

        incidentProxy.updateIncidentStatus(resolutionDto.getIncidentId(), resolutionDto.getStatus());

        log.info("Successfully recorded Resolution ID: {} and triggered status update for Incident: {}",
                savedRes.getResolutionId(), resolutionDto.getIncidentId());

        return new SuccessResponseProjection<>(true, "Resolution recorded", mapToProjection(savedRes));
    }

    public SuccessResponseProjection<ResolutionProjection> getResolutionByIncident(Long incidentId) {
        log.info("Service request: Fetching resolution for Incident ID: {}", incidentId);
        ResolutionProjection projection = resolutionRepository.findResolutionByIncidentId(incidentId);

        if (projection == null) {
            throw new ResolutionException("No resolution found for this incident", HttpStatus.NOT_FOUND);
        }

        return new SuccessResponseProjection<>(true, "Resolution retrieved successfully", projection);
    }

    @Transactional
    public SuccessResponseProjection<ResolutionProjection> updateResolution(Long resolutionId, ResolutionDto resolutionDto) {
        log.info("Service request: Updating Resolution ID: {}", resolutionId);

        Resolution resolution = resolutionRepository.findById(resolutionId)
                .orElseThrow(() -> new ResolutionException("Resolution not found", HttpStatus.NOT_FOUND));

        if (resolutionDto.getActions() != null) {
            resolution.setActions(resolutionDto.getActions());
        }

        if (resolutionDto.getStatus() != null) {
            resolution.setStatus(resolutionDto.getStatus());
            incidentProxy.updateIncidentStatus(resolution.getIncidentId(), resolutionDto.getStatus());
        }

        Resolution updatedRes = resolutionRepository.save(resolution);
        return new SuccessResponseProjection<>(true, "Resolution updated", mapToProjection(updatedRes));
    }

    public SuccessResponseProjection<List<ResolutionProjection>> getUserResolution(Long userId) {
        log.info("Service request: Retrieving history for Officer ID: {}", userId);

        List<ResolutionProjection> resolutions = resolutionRepository.getUserResolution(userId);
        if (resolutions.isEmpty()) {
            throw new ResolutionException("Resolutions not found", HttpStatus.NOT_FOUND);
        }

        return new SuccessResponseProjection<>(true, "User resolutions retrieved successfully", resolutions);
    }

    private ResolutionProjection mapToProjection(Resolution res) {
        return new ResolutionProjection(
                res.getResolutionId(),
                res.getIncidentId(),
                res.getOfficerId(),
                res.getActions(),
                res.getDate(),
                res.getStatus()
        );
    }
}