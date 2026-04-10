package com.cognizant.trainingservice.service;

import com.cognizant.trainingservice.dto.TrainingDto;
import com.cognizant.trainingservice.projection.SuccessResponseProjection;
import com.cognizant.trainingservice.projection.TrainingProjection;

import java.util.List;

public interface ITrainingService {
    public SuccessResponseProjection<TrainingProjection> enrollStaff(TrainingDto trainingDto);

    public SuccessResponseProjection<List<TrainingProjection>> getTrainingsByProgram(Long programId);

    public SuccessResponseProjection<List<TrainingProjection>> getUserTrainings(Long userId);

    public SuccessResponseProjection<TrainingProjection> updateTraining(Long trainingId, TrainingDto trainingDto);
}
