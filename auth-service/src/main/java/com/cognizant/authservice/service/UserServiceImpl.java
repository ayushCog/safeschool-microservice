package com.cognizant.authservice.service;

import com.cognizant.authservice.classexception.AuthException;
import com.cognizant.authservice.dto.UserRegistrationDto;
import com.cognizant.authservice.entity.Auth;
import com.cognizant.authservice.projection.SuccessResponseProjection;
import com.cognizant.authservice.projection.UserProjection;
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
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserProxy userProxy;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @CircuitBreaker(name="userRegister")
    public SuccessResponseProjection<UserProjection> addUser(UserRegistrationDto userRegistrationDto) {
        String rawPassword = userRegistrationDto.getPassword();
        userRegistrationDto.setPassword(null);

        log.info("Forwarding registration request to User Service for email: {}", userRegistrationDto.getEmail());

        ResponseEntity<SuccessResponseProjection<UserProjection>> response = userProxy.registerUser(userRegistrationDto);

        if (response.getBody() != null && response.getBody().isSuccess()) {
            UserProjection user = response.getBody().getData();

            Auth authUser = new Auth();
            authUser.setEmailId(userRegistrationDto.getEmail());
            authUser.setPassword(passwordEncoder.encode(rawPassword));
            authUser.setRole(userRegistrationDto.getRole());

            authRepository.save(authUser);
            log.info("User successfully registered and authenticated");
        }
        else {
            if(response.getBody() != null){
                throw new AuthException("Hi " + response.getBody().getMessage() + response.getBody().isSuccess(), HttpStatus.resolve(response.getStatusCode().value()));
            }
            else {
                log.error("Failed to create user");
                throw new AuthException("Could not complete registration", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new SuccessResponseProjection<UserProjection>(true, "User created successfully", response.getBody().getData());
    }
}
