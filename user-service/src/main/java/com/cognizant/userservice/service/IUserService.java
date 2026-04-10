package com.cognizant.userservice.service;

import com.cognizant.userservice.dto.UserRegistrationDto;
import com.cognizant.userservice.projection.SuccessResponseProjection;
import com.cognizant.userservice.projection.UserProjection;

import java.util.List;

public interface IUserService {
    public SuccessResponseProjection<UserProjection> addUser(UserRegistrationDto userRegistrationDto);
    public SuccessResponseProjection<List<Long>> getAllUserIds();
    public SuccessResponseProjection<List<Long>> getUserIdsByRole(String role);
    public SuccessResponseProjection<UserProjection> getUserById(Long id);
    public SuccessResponseProjection<Boolean> checkUserExists(Long id);
}
