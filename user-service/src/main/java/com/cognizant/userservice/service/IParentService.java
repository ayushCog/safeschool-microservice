package com.cognizant.userservice.service;

import com.cognizant.userservice.dto.ParentRegistrationDto;
import com.cognizant.userservice.projection.ParentProjection;
import com.cognizant.userservice.projection.SuccessResponseProjection;

public interface IParentService {
    public SuccessResponseProjection<ParentProjection> addParent(ParentRegistrationDto parentRegistrationDto);
}
