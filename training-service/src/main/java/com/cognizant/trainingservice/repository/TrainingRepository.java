package com.cognizant.trainingservice.repository;

import com.cognizant.trainingservice.entity.Training;
import com.cognizant.trainingservice.projection.TrainingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT new com.cognizant.trainingservice.projection.TrainingProjection(t.trainingId, t.programId, t.userId, t.completionDate, t.status) FROM Training t WHERE t.programId = :programId")
    List<TrainingProjection> findAllByProgramId(@Param("programId") Long programId);

    @Query("SELECT new com.cognizant.trainingservice.projection.TrainingProjection(t.trainingId, t.programId, t.userId, t.completionDate, t.status) FROM Training t WHERE t.userId = :userId")
    List<TrainingProjection> findAllByUserId(@Param("userId") Long userId);
}
