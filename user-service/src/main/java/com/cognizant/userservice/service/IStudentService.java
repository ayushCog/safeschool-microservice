package com.cognizant.userservice.service;

import com.cognizant.userservice.dto.StudentRegistrationDto;
import com.cognizant.userservice.projection.StudentProjection;
import com.cognizant.userservice.projection.SuccessResponseProjection;

public interface IStudentService {
    public SuccessResponseProjection<StudentProjection> addStudent(StudentRegistrationDto studentRegistrationDto);
}
