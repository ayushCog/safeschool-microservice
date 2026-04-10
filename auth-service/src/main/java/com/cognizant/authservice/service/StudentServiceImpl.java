package com.cognizant.authservice.service;

import com.cognizant.authservice.classexception.AuthException;
import com.cognizant.authservice.dto.StudentRegistrationDto;
import com.cognizant.authservice.entity.Auth;
import com.cognizant.authservice.projection.StudentProjection;
import com.cognizant.authservice.projection.SuccessResponseProjection;
import com.cognizant.authservice.proxy.UserProxy;
import com.cognizant.authservice.repository.AuthRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StudentServiceImpl implements IStudentService{
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @CircuitBreaker(name="studentRegister")
    public SuccessResponseProjection<StudentProjection> addStudent(StudentRegistrationDto studentRegistrationDto) {
        String rawPassword = studentRegistrationDto.getPassword();
        studentRegistrationDto.setPassword(null);

        log.info("Forwarding student registration request to User Service for email: {}", studentRegistrationDto.getEmail());

        ResponseEntity<SuccessResponseProjection<StudentProjection>> studentResponse = userProxy.registerStudent(studentRegistrationDto);

        if (studentResponse.getBody() != null && studentResponse.getBody().isSuccess()) {
            StudentProjection user = studentResponse.getBody().getData();

            log.info("Forwarding to Student Service for profile creation");

                Auth authUser = new Auth();
                authUser.setEmailId(studentRegistrationDto.getEmail());
                authUser.setPassword(passwordEncoder.encode(rawPassword));
                authUser.setRole(studentRegistrationDto.getRole());

                authRepository.save(authUser);
                log.info("Student successfully registered and authenticated");
        }
        else {
            if (studentResponse.getBody() != null) {
                throw new AuthException(studentResponse.getBody().getMessage(), HttpStatus.resolve(studentResponse.getStatusCode().value()));
            } else {
                log.error("Failed to create student base user");
                throw new AuthException("Could not complete student registration", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new SuccessResponseProjection<StudentProjection>(true, "Student created successfully", studentResponse.getBody().getData());
    }
}
