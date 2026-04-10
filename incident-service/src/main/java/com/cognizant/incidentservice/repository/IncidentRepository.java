package com.cognizant.incidentservice.repository;

import com.cognizant.incidentservice.entity.Incident;
import com.cognizant.incidentservice.projection.IncidentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    @Query("select new com.cognizant.incidentservice.projection.IncidentProjection(i.incidentId, i.reporterId, i.type, i.location, i.date, i.status) from Incident i")
    List<IncidentProjection> getAllIncidents();

    @Query("select new com.cognizant.incidentservice.projection.IncidentProjection(i.incidentId, i.reporterId, i.type, i.location, i.date, i.status) from Incident i where i.reporterId= :userId")
    List<IncidentProjection> getUserIncidents(@Param("userId") Long userId);
}
