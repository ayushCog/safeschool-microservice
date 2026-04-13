package com.cognizant.complianceservice.controller;

import com.cognizant.complianceservice.dto.ComplianceRecordDto;
import com.cognizant.complianceservice.projection.ComplianceProjection;
import com.cognizant.complianceservice.projection.SuccessResponseProjection;
import com.cognizant.complianceservice.service.IComplianceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compliance")
@Slf4j
public class ComplianceController {
    @Autowired
    private IComplianceService complianceServiceImpl;

    @PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @GetMapping("/")
    public ResponseEntity<SuccessResponseProjection<List<ComplianceProjection>>> getAllComplianceRecords() {
        log.info("Received GET request: Fetching all school compliance records");
        return ResponseEntity.status(HttpStatus.OK).body(complianceServiceImpl.getAllComplianceRecords());
    }

    @PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<ComplianceProjection>> saveCompliance(@Valid @RequestBody ComplianceRecordDto dto) {
        log.info("Received POST request: Logging new {} compliance for Entity ID: {}", dto.getType(), dto.getEntityId());
        return ResponseEntity.status(HttpStatus.CREATED).body(complianceServiceImpl.saveComplianceFromDto(dto));
    }
}
