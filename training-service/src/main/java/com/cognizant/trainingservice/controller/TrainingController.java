package com.cognizant.trainingservice.controller;

import com.cognizant.trainingservice.dto.TrainingDto;
import com.cognizant.trainingservice.projection.SuccessResponseProjection;
import com.cognizant.trainingservice.projection.TrainingProjection;
import com.cognizant.trainingservice.service.ITrainingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
@Slf4j
public class TrainingController {
    @Autowired
    private ITrainingService trainingServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<TrainingProjection>> enrollStaff(@Valid @RequestBody TrainingDto trainingDto) {
        log.info("Received POST request: Enrolling Staff ID: {} into Program ID: {}", trainingDto.getUserId(), trainingDto.getProgramId());
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingServiceImpl.enrollStaff(trainingDto));
    }

    //@PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @GetMapping("/program/{programId}")
    public ResponseEntity<SuccessResponseProjection<List<TrainingProjection>>> getTrainingsByProgram(@PathVariable Long programId) {
        log.info("Received GET request: Fetching all staff enrollments for Program ID: {}", programId);
        return ResponseEntity.status(HttpStatus.OK).body(trainingServiceImpl.getTrainingsByProgram(programId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<TrainingProjection>>> getUserTrainings(@PathVariable Long userId) {
        log.info("Received GET request: Fetching training history for Staff ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(trainingServiceImpl.getUserTrainings(userId));
    }

    @PutMapping("/update/{trainingId}")
    public ResponseEntity<SuccessResponseProjection<TrainingProjection>> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        log.info("Received PUT request: Updating status for Training ID: {}", trainingId);
        return ResponseEntity.status(HttpStatus.OK).body(trainingServiceImpl.updateTraining(trainingId, trainingDto));
    }
}
