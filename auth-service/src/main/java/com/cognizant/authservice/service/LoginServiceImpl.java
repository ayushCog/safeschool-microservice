package com.cognizant.authservice.service;

import com.cognizant.authservice.dto.LoginRequestDto;
import com.cognizant.authservice.entity.Auth;
import com.cognizant.authservice.classexception.AuthException;
import com.cognizant.authservice.projection.AuthResponseProjection;
import com.cognizant.authservice.projection.SuccessResponseProjection;
import com.cognizant.authservice.repository.AuthRepository;
import com.cognizant.authservice.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtil jwtUtil;

    public SuccessResponseProjection<AuthResponseProjection> login(LoginRequestDto request){
        log.info("Received login request for user: {}", request.getEmail());

        Auth user = authRepository.findById(request.getEmail())
                .orElseThrow(() -> new AuthException("Invalid email or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Login failed: Password mismatch for user {}", request.getEmail());
            throw new AuthException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        String jwt = jwtUtil.generateToken(user.getEmailId(), user.getRole());

        AuthResponseProjection authData = new AuthResponseProjection(jwt);
        return new SuccessResponseProjection<>(true, "User logged in successfully", authData);
    }
}
