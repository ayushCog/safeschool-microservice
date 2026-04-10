package com.cognizant.resolutionservice.service;

import com.cognizant.resolutionservice.dto.ResolutionDto;
import com.cognizant.resolutionservice.projection.ResolutionProjection;
import com.cognizant.resolutionservice.projection.SuccessResponseProjection;

import java.util.List;

public interface IResolutionService {
    public SuccessResponseProjection<ResolutionProjection> createResolution(ResolutionDto resolutionDto);

    public SuccessResponseProjection<ResolutionProjection> getResolutionByIncident(Long incidentId);

    public SuccessResponseProjection<ResolutionProjection> updateResolution(Long resolutionId, ResolutionDto resolutionDto);

    public SuccessResponseProjection<List<ResolutionProjection>> getUserResolution(Long userId);
}
