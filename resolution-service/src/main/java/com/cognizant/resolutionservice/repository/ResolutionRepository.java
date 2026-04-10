package com.cognizant.resolutionservice.repository;

import com.cognizant.resolutionservice.entity.Resolution;
import com.cognizant.resolutionservice.projection.ResolutionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Long> {
    @Query("select new com.cognizant.resolutionservice.projection.ResolutionProjection(r.resolutionId, r.incidentId, r.officerId, r.actions, r.date, r.status) from Resolution r where r.incidentId= :incidentId")
    public ResolutionProjection findResolutionByIncidentId(@Param("incidentId") Long incidentId);

    @Query("select new com.cognizant.resolutionservice.projection.ResolutionProjection(r.resolutionId, r.incidentId, r.officerId, r.actions, r.date, r.status) from Resolution r where r.officerId= :userId")
    List<ResolutionProjection> getUserResolution(@Param("userId") Long userId);
}
