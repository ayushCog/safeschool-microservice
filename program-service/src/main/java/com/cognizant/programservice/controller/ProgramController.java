package com.cognizant.programservice.controller;

import com.cognizant.programservice.dto.ProgramDto;
import com.cognizant.programservice.projection.ProgramProjection;
import com.cognizant.programservice.projection.SuccessResponseProjection;
import com.cognizant.programservice.service.IProgramService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/program")
@Slf4j
public class ProgramController {
    @Autowired
    IProgramService programServiceImpl;

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @GetMapping("/")
    public ResponseEntity<SuccessResponseProjection<List<ProgramProjection>>> getAllPrograms(){
        log.info("Received GET request: Fetching all safety programs");
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.getAllPrograms());
    }

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<ProgramProjection>> createProgram(@Valid @RequestBody ProgramDto programDto){
        log.info("Received POST request: Creating new program: '{}' with Status: {}", programDto.getTitle(), programDto.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.createProgram(programDto));
    }

    @GetMapping("/{programId}")
    public ResponseEntity<SuccessResponseProjection<ProgramProjection>> getProgram(@PathVariable Long programId){
        log.info("Received GET request: Fetching details for Program ID: {}", programId);
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.getProgram(programId));
    }

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @PutMapping("/update/{programId}")
    public ResponseEntity<SuccessResponseProjection<ProgramProjection>> updateProgram(@PathVariable Long programId, @RequestBody ProgramDto programDto){
        log.info("Received PUT request: Updating Program ID: {}", programId);
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.updateProgram(programId, programDto));
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<SuccessResponseProjection<Boolean>> checkProgramExists(@PathVariable("id") Long id) {
        log.info("Received GET request: Checking existence for Program ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.checkProgramExists(id));
    }
}
