package com.cognizant.userservice.controller;

import com.cognizant.userservice.dto.ParentRegistrationDto;
import com.cognizant.userservice.projection.ParentProjection;
import com.cognizant.userservice.projection.SuccessResponseProjection;
import com.cognizant.userservice.service.IParentService;
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
@RequestMapping("/parent")
@Slf4j
public class ParentController {
    @Autowired
    private IParentService parentService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponseProjection<ParentProjection>> registerParent(@Valid @RequestBody ParentRegistrationDto parentRegistrationDto) {
        log.info("Received POST request: Parent registration attempt for Email: {} linked to Student: {}", parentRegistrationDto.getEmail(), parentRegistrationDto.getStudentEmail());
        return ResponseEntity.status(HttpStatus.OK).body(parentService.addParent(parentRegistrationDto));
    }
}
