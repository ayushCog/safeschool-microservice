package com.cognizant.trainingservice.service;

import com.cognizant.trainingservice.proxy.ProgramProxy;
import com.cognizant.trainingservice.proxy.UserProxy;
import com.cognizant.trainingservice.classexception.TrainingException;
import com.cognizant.trainingservice.dto.TrainingDto;
import com.cognizant.trainingservice.entity.Training;
import com.cognizant.trainingservice.projection.SuccessResponseProjection;
import com.cognizant.trainingservice.projection.TrainingProjection;
import com.cognizant.trainingservice.repository.TrainingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TrainingServiceImpl implements ITrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private ProgramProxy programProxy;

    @Autowired
    private UserProxy userProxy;

    @Transactional
    @CircuitBreaker(name="checkProgramAndUser")
    public SuccessResponseProjection<TrainingProjection> enrollStaff(TrainingDto dto) {
        log.info("Service request: Enrolling Staff ID: {} into Program ID: {}", dto.getUserId(), dto.getProgramId());

        ResponseEntity<SuccessResponseProjection<Boolean>> programRes = programProxy.checkProgramExists(dto.getProgramId());
        if (programRes.getBody() == null || !programRes.getBody().getData()) {
            log.error("Enrollment failed: Program ID {} not found", dto.getProgramId());
            throw new TrainingException("Program not found", HttpStatus.NOT_FOUND);
        }

        ResponseEntity<SuccessResponseProjection<Boolean>> userRes = userProxy.checkUserExists(dto.getUserId());
        if (userRes.getBody() == null || !userRes.getBody().getData()) {
            log.error("Enrollment failed: User ID {} not found", dto.getUserId());
            throw new TrainingException("User not found", HttpStatus.NOT_FOUND);
        }

        Training training = new Training();
        training.setProgramId(dto.getProgramId());
        training.setUserId(dto.getUserId());
        training.setStatus(dto.getStatus() != null ? dto.getStatus() : "ENROLLED");
        training.setCompletionDate(dto.getCompletionDate());

        Training savedTraining = trainingRepository.save(training);

        log.info("Successfully enrolled Staff ID: {} in Program ID: {}. Training ID: {}",
                dto.getUserId(), dto.getProgramId(), savedTraining.getTrainingId());

        return new SuccessResponseProjection<>(true, "Staff enrolled successfully", mapToProjection(savedTraining));
    }

    public SuccessResponseProjection<List<TrainingProjection>> getTrainingsByProgram(Long programId) {
        log.info("Service request: Retrieving training records for Program ID: {}", programId);

        List<TrainingProjection> programTrainings = trainingRepository.findAllByProgramId(programId);

        return new SuccessResponseProjection<>(true, "Program trainings retrieved", programTrainings);
    }

    @Transactional
    public SuccessResponseProjection<TrainingProjection> updateTraining(Long trainingId, TrainingDto dto) {
        log.info("Service request: Updating Training ID: {}", trainingId);

        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> {
                    log.error("Update failed: Training record ID {} not found", trainingId);
                    return new TrainingException("Training record not found", HttpStatus.NOT_FOUND);
                });

        if (dto.getStatus() != null) {
            training.setStatus(dto.getStatus());
            if ("COMPLETED".equalsIgnoreCase(dto.getStatus()) && training.getCompletionDate() == null) {
                training.setCompletionDate(LocalDate.now());
            }
        }

        if (dto.getCompletionDate() != null) {
            training.setCompletionDate(dto.getCompletionDate());
        }

        Training updatedTraining = trainingRepository.save(training);

        log.info("Successfully updated Training ID: {}", trainingId);
        return new SuccessResponseProjection<>(true, "Training updated successfully", mapToProjection(updatedTraining));
    }

    public SuccessResponseProjection<List<TrainingProjection>> getUserTrainings(Long userId) {
        log.info("Service request: Fetching training history for Staff ID: {}", userId);

        List<TrainingProjection> userTrainings = trainingRepository.findAllByUserId(userId);

        return new SuccessResponseProjection<>(true, "Staff training history retrieved", userTrainings);
    }

    private TrainingProjection mapToProjection(Training t) {
        return new TrainingProjection(
                t.getTrainingId(),
                t.getProgramId(),
                t.getUserId(),
                t.getCompletionDate(),
                t.getStatus()
        );
    }
}