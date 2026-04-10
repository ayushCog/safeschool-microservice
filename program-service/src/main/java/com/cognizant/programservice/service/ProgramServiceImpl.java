package com.cognizant.programservice.service;

import com.cognizant.programservice.classexception.ProgramException;
import com.cognizant.programservice.dto.ProgramDto;
import com.cognizant.programservice.entity.Program;
import com.cognizant.programservice.projection.ProgramProjection;
import com.cognizant.programservice.projection.SuccessResponseProjection;
import com.cognizant.programservice.repository.ProgramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProgramServiceImpl implements IProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Transactional
    public SuccessResponseProjection<ProgramProjection> createProgram(ProgramDto programDto) {
        log.info("Service request: Initiating creation of program: {}", programDto.getTitle());

        Program program = new Program();
        program.setTitle(programDto.getTitle());
        program.setDescription(programDto.getDescription());
        program.setStartDate(programDto.getStartDate());
        program.setEndDate(programDto.getEndDate());
        program.setStatus(programDto.getStatus() != null ? programDto.getStatus() : "ACTIVE");

        Program savedProgram = programRepository.save(program);

        log.info("Successfully created Program ID: {}", savedProgram.getProgramId());

        return new SuccessResponseProjection<>(true, "Program created successfully", mapToProjection(savedProgram));
    }

    public SuccessResponseProjection<List<ProgramProjection>> getAllPrograms() {
        log.info("Service request: Retrieving all programs");

        List<ProgramProjection> programs = programRepository.getAllPrograms();

        log.info("Successfully retrieved {} programs", programs.size());

        return new SuccessResponseProjection<>(true, "Programs retrieved successfully", programs);
    }

    @Transactional
    public SuccessResponseProjection<ProgramProjection> updateProgram(Long programId, ProgramDto programDto) {
        log.info("Service request: Updating Program ID: {}", programId);

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> {
                    log.error("Update failed: Program ID: {} not found", programId);
                    return new ProgramException("Program not found with id: " + programId, HttpStatus.NOT_FOUND);
                });

        if(programDto.getTitle() != null) program.setTitle(programDto.getTitle());
        if(programDto.getDescription() != null) program.setDescription(programDto.getDescription());
        if(programDto.getStartDate() != null) program.setStartDate(programDto.getStartDate());
        if(programDto.getEndDate() != null) program.setEndDate(programDto.getEndDate());
        if(programDto.getStatus() != null) program.setStatus(programDto.getStatus());

        Program updatedProgram = programRepository.save(program);

        log.info("Successfully updated Program ID: {}", programId);

        return new SuccessResponseProjection<>(true, "Program updated successfully", mapToProjection(updatedProgram));
    }

    public SuccessResponseProjection<ProgramProjection> getProgram(Long programId) {
        log.info("Service request: Fetching Program ID: {}", programId);

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> {
                    log.error("Fetch failed: Program ID: {} not found", programId);
                    return new ProgramException("Program not found with id: " + programId, HttpStatus.NOT_FOUND);
                });

        return new SuccessResponseProjection<>(true, "Program retrieved successfully", mapToProjection(program));
    }

    public SuccessResponseProjection<Boolean> checkProgramExists(Long id) {
        log.info("Service: Checking existence of program ID: {}", id);
        boolean exists = programRepository.existsById(id);

        if (exists) {
            log.info("Service: Program {} found", id);
        } else {
            log.warn("Service: Program {} not found", id);
        }

        return new SuccessResponseProjection<>(true, "Program existence check completed", exists);
    }

    private ProgramProjection mapToProjection(Program program) {
        return new ProgramProjection(
                program.getProgramId(),
                program.getTitle(),
                program.getDescription(),
                program.getStartDate(),
                program.getEndDate(),
                program.getStatus()
        );
    }
}