package com.cognizant.userservice.service;

import com.cognizant.userservice.classexception.UserException;
import com.cognizant.userservice.dto.UserRegistrationDto;
import com.cognizant.userservice.entity.User;
import com.cognizant.userservice.projection.SuccessResponseProjection;
import com.cognizant.userservice.projection.UserProjection;
import com.cognizant.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    public SuccessResponseProjection<UserProjection> addUser(UserRegistrationDto userRegistrationDto) throws UserException {
        log.info("Received service request: User registration attempt for Email: {} with Role: {}", userRegistrationDto.getEmail(), userRegistrationDto.getRole());

        if(userRepository.findUserByEmail(userRegistrationDto.getEmail()) != null){
            log.error("Registration failed: User with Email: {} already exists", userRegistrationDto.getEmail());
            throw new UserException("User already registered", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setRole(userRegistrationDto.getRole().toUpperCase());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPhone(userRegistrationDto.getPhone());
        user.setStatus(userRegistrationDto.getStatus());

        User savedUser = userRepository.save(user);

        log.info("Successfully registered User: {} with ID: {}", savedUser.getEmail(), savedUser.getUserId());

        return new SuccessResponseProjection<>(true, "User created successfully", new UserProjection(savedUser.getUserId(), savedUser.getName(), savedUser.getRole(), savedUser.getEmail(), savedUser.getPhone(), savedUser.getStatus()));
    }

    public SuccessResponseProjection<List<Long>> getAllUserIds() {
        log.info("Service: Fetching all user IDs from database");
        List<Long> ids = userRepository.findAllIds();
        log.info("Service: Successfully retrieved {} user IDs", ids.size());

        return new SuccessResponseProjection<>(true, "All user IDs fetched", ids);
    }

    public SuccessResponseProjection<List<Long>> getUserIdsByRole(String role) {
        log.info("Service: Fetching user IDs with role: {}", role);
        List<Long> ids = userRepository.findIdsByRole(role);
        log.info("Service: Found {} users with role: {}", ids.size(), role);

        return new SuccessResponseProjection<>(true, "User IDs fetched for role: " + role, ids);
    }

    public SuccessResponseProjection<UserProjection> getUserById(Long id) {
        log.info("Service: Attempting to fetch user details for ID: {}", id);

        UserProjection user = userRepository.findUserById(id)
                .orElseThrow(() -> {
                    log.error("Service: User NOT found with ID: {}", id);
                    return new RuntimeException("User not found with id: " + id);
                });

        log.info("Service: Successfully found user for ID: {}", id);
        return new SuccessResponseProjection<>(true, "User details fetched", user);
    }

    public SuccessResponseProjection<Boolean> checkUserExists(Long id) {
        log.info("Service: Checking database for existence of User ID: {}", id);
        boolean exists = userRepository.existsById(id);

        if (exists) {
            log.info("Service: User ID {} exists", id);
        } else {
            log.warn("Service: User ID {} does not exist", id);
        }

        return new SuccessResponseProjection<>(true, "Existence check completed", exists);
    }
}
