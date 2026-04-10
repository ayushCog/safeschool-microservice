package com.cognizant.authservice.service;

import com.cognizant.authservice.classexception.AuthException;
import com.cognizant.authservice.dto.ParentRegistrationDto;
import com.cognizant.authservice.entity.Auth;
import com.cognizant.authservice.projection.ParentProjection;
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
public class ParentServiceImpl implements IParentService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @CircuitBreaker(name="parentRegister")
    public SuccessResponseProjection<ParentProjection> addParent(ParentRegistrationDto parentRegistrationDto) {
        String rawPassword = parentRegistrationDto.getPassword();
        parentRegistrationDto.setPassword(null);

        log.info("Forwarding parent registration request to User Service for email: {}", parentRegistrationDto.getEmail());

        ResponseEntity<SuccessResponseProjection<ParentProjection>> parentResponse = userProxy.registerParent(parentRegistrationDto);

        if (parentResponse.getBody() != null && parentResponse.getBody().isSuccess()) {
            ParentProjection user = parentResponse.getBody().getData();

            log.info("Forwarding to Parent Service for profile creation");

            Auth authUser = new Auth();
            authUser.setEmailId(parentRegistrationDto.getEmail());
            authUser.setPassword(passwordEncoder.encode(rawPassword));
            authUser.setRole(parentRegistrationDto.getRole());

            authRepository.save(authUser);
            log.info("Parent successfully registered and authenticated");
        }
        else {
            if (parentResponse.getBody() != null) {
                throw new AuthException(parentResponse.getBody().getMessage(), HttpStatus.resolve(parentResponse.getStatusCode().value()));
            } else {
                log.error("Failed to create parent base user");
                throw new AuthException("Could not complete parent registration", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new SuccessResponseProjection<ParentProjection>(true, "Parent created successfully", parentResponse.getBody().getData());
    }
}
