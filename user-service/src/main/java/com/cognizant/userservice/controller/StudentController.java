package com.cognizant.userservice.controller;

import com.cognizant.userservice.dto.StudentRegistrationDto;
import com.cognizant.userservice.projection.StudentProjection;
import com.cognizant.userservice.projection.SuccessResponseProjection;
import com.cognizant.userservice.service.IStudentService;
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
@RequestMapping("/student")
@Slf4j
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponseProjection<StudentProjection>> registerStudent(@Valid @RequestBody StudentRegistrationDto studentRegistrationDto) {
        log.info("Received POST request: Student registration attempt for Email: {}", studentRegistrationDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(studentService.addStudent(studentRegistrationDto));
    }
}
