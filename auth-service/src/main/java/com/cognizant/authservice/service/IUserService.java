package com.cognizant.authservice.service;

import com.cognizant.authservice.dto.UserRegistrationDto;
import com.cognizant.authservice.projection.SuccessResponseProjection;
import com.cognizant.authservice.projection.UserProjection;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    public SuccessResponseProjection<UserProjection> addUser(UserRegistrationDto userRegistrationDto);
}
