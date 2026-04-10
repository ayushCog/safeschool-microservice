package com.cognizant.programservice.repository;

import com.cognizant.programservice.entity.Program;
import com.cognizant.programservice.projection.ProgramProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("select new com.cognizant.programservice.projection.ProgramProjection(p.programId, p.title, p.description, p.startDate, p.endDate, p.status) from Program p")
    List<ProgramProjection> getAllPrograms();
}
