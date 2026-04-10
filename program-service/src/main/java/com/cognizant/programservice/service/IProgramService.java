package com.cognizant.programservice.service;

import com.cognizant.programservice.dto.ProgramDto;
import com.cognizant.programservice.projection.ProgramProjection;
import com.cognizant.programservice.projection.SuccessResponseProjection;

import java.util.List;

public interface IProgramService {
    public SuccessResponseProjection<ProgramProjection> createProgram(ProgramDto programDto);

    public SuccessResponseProjection<List<ProgramProjection>> getAllPrograms();

    public SuccessResponseProjection<ProgramProjection> updateProgram(Long programId, ProgramDto programDto);

    public SuccessResponseProjection<ProgramProjection> getProgram(Long programId);

    public SuccessResponseProjection<Boolean> checkProgramExists(Long id);
}
