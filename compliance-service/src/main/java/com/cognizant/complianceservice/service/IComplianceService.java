package com.cognizant.complianceservice.service;

import com.cognizant.complianceservice.dto.ComplianceRecordDto;
import com.cognizant.complianceservice.projection.ComplianceProjection;
import com.cognizant.complianceservice.projection.SuccessResponseProjection;

import java.util.List;

public interface IComplianceService {
    public SuccessResponseProjection<List<ComplianceProjection>> getAllComplianceRecords();

    public SuccessResponseProjection<ComplianceProjection> saveComplianceFromDto(ComplianceRecordDto dto);
}
