package com.cognizant.authservice.service;

import com.cognizant.authservice.dto.LoginRequestDto;
import com.cognizant.authservice.projection.AuthResponseProjection;
import com.cognizant.authservice.projection.SuccessResponseProjection;

public interface ILoginService {
    public SuccessResponseProjection<AuthResponseProjection> login(LoginRequestDto request);
}
