package com.cognizant.authservice.service;

import com.cognizant.authservice.dto.ParentRegistrationDto;
import com.cognizant.authservice.projection.ParentProjection;
import com.cognizant.authservice.projection.SuccessResponseProjection;

public interface IParentService {
    public SuccessResponseProjection<ParentProjection> addParent(ParentRegistrationDto parentRegistrationDto);
}
