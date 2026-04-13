package com.cognizant.complianceservice.repository;

import com.cognizant.complianceservice.entity.ComplianceRecord;
import com.cognizant.complianceservice.projection.ComplianceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRepository extends JpaRepository<ComplianceRecord, Long> {
    @Query("SELECT new com.cognizant.complianceservice.projection.ComplianceProjection(c.complianceId, c.entityId, c.type, c.result, c.date, c.notes) FROM ComplianceRecord c")
    List<ComplianceProjection> getAllComplianceRecords();
}
