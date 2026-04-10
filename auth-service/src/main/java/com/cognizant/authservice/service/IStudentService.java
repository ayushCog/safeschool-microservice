package com.cognizant.authservice.service;

import com.cognizant.authservice.dto.StudentRegistrationDto;
import com.cognizant.authservice.projection.StudentProjection;
import com.cognizant.authservice.projection.SuccessResponseProjection;
import com.cognizant.authservice.projection.UserProjection;
import org.springframework.http.ResponseEntity;

public interface IStudentService {
    public SuccessResponseProjection<StudentProjection> addStudent(StudentRegistrationDto studentRegistrationDto);
}
