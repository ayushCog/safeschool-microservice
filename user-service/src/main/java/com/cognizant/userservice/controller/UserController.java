package com.cognizant.userservice.controller;

import com.cognizant.userservice.dto.UserRegistrationDto;
import com.cognizant.userservice.projection.SuccessResponseProjection;
import com.cognizant.userservice.projection.UserProjection;
import com.cognizant.userservice.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponseProjection<UserProjection>> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        log.info("Received POST request: Registration attempt for User: {} with Role: {}", userRegistrationDto.getEmail(), userRegistrationDto.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(userRegistrationDto));
    }

    @GetMapping("/all-ids")
    public ResponseEntity<SuccessResponseProjection<List<Long>>> getAllUserIds() {
        log.info("Received GET request: Fetching all user IDs");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUserIds());
    }

    @GetMapping("/all-ids/role/{role}")
    public ResponseEntity<SuccessResponseProjection<List<Long>>> getUserIdsByRole(@PathVariable("role") String role) {
        log.info("Received GET request: Fetching user IDs for role: {}", role);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserIdsByRole(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseProjection<UserProjection>> getUserById(@PathVariable("id") Long id) {
        log.info("Received GET request: Fetching user details for ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<SuccessResponseProjection<Boolean>> checkUserExists(@PathVariable("id") Long id) {
        log.info("Received GET request: Checking existence for User ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkUserExists(id));
    }
}
