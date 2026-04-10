package com.cognizant.authservice.controller;

import com.cognizant.authservice.dto.LoginRequestDto;
import com.cognizant.authservice.dto.ParentRegistrationDto;
import com.cognizant.authservice.dto.StudentRegistrationDto;
import com.cognizant.authservice.dto.UserRegistrationDto;
import com.cognizant.authservice.projection.*;
import com.cognizant.authservice.service.ILoginService;
import com.cognizant.authservice.service.IParentService;
import com.cognizant.authservice.service.IStudentService;
import com.cognizant.authservice.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IParentService parentService;

    @Autowired
    private ILoginService loginService;

    @PostMapping("/register/user")
    public ResponseEntity<SuccessResponseProjection<UserProjection>> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        log.info("Received POST request: Registration attempt for User: {} with Role: {}", userRegistrationDto.getEmail(), userRegistrationDto.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(userRegistrationDto));
    }

    @PostMapping("/register/student")
    public ResponseEntity<SuccessResponseProjection<StudentProjection>> registerStudent(@Valid @RequestBody StudentRegistrationDto studentRegistrationDto) {
        log.info("Received POST request: Student registration attempt for Email: {}", studentRegistrationDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(studentService.addStudent(studentRegistrationDto));
    }

    @PostMapping("/register/parent")
    public ResponseEntity<SuccessResponseProjection<ParentProjection>> registerParent(@Valid @RequestBody ParentRegistrationDto parentRegistrationDto) {
        log.info("Received POST request: Parent registration attempt for Email: {} linked to Student: {}", parentRegistrationDto.getEmail(), parentRegistrationDto.getStudentEmail());
        return ResponseEntity.status(HttpStatus.OK).body(parentService.addParent(parentRegistrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponseProjection<AuthResponseProjection>> login(@Valid @RequestBody LoginRequestDto request) {
        log.info("Received POST request: Authentication attempt for User: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(loginService.login(request));
    }
}
