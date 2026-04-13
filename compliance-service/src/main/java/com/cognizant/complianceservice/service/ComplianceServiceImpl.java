package com.cognizant.complianceservice.service;

import com.cognizant.complianceservice.classexception.ComplianceException;
import com.cognizant.complianceservice.dto.ComplianceRecordDto;
import com.cognizant.complianceservice.entity.ComplianceRecord;
import com.cognizant.complianceservice.projection.ComplianceProjection;
import com.cognizant.complianceservice.projection.SuccessResponseProjection;
import com.cognizant.complianceservice.proxy.UserProxy;
import com.cognizant.complianceservice.repository.ComplianceRepository;
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
public class ComplianceServiceImpl implements IComplianceService {
    @Autowired
    private ComplianceRepository complianceRepository;

    @Autowired
    private UserProxy userProxy;

    public SuccessResponseProjection<List<ComplianceProjection>> getAllComplianceRecords() {
        log.info("Service request: Retrieving all compliance records");
        List<ComplianceProjection> records = complianceRepository.getAllComplianceRecords();
        return new SuccessResponseProjection<>(true, "Compliance records retrieved successfully", records);
    }

    @Transactional
    public SuccessResponseProjection<ComplianceProjection> saveComplianceFromDto(ComplianceRecordDto dto) {
        log.info("Service request: Saving compliance for Entity ID: {}", dto.getEntityId());

        ResponseEntity<SuccessResponseProjection<Boolean>> userRes = userProxy.checkUserExists(dto.getEntityId());

        if (userRes.getBody() == null || !userRes.getBody().getData()) {
            log.error("Compliance failed: Officer ID {} not found in User Service", dto.getEntityId());
            throw new ComplianceException("Officer not found", HttpStatus.NOT_FOUND);
        }

        ComplianceRecord record = new ComplianceRecord();
        record.setEntityId(dto.getEntityId());
        record.setEntityId(dto.getEntityId());
        record.setType(dto.getType());
        record.setResult(dto.getResult());
        record.setNotes(dto.getNotes());
        record.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());

        ComplianceRecord savedRecord = complianceRepository.save(record);
        log.info("Compliance record persisted with ID: {}", savedRecord.getComplianceId());

        return new SuccessResponseProjection<>(true, "Compliance record logged successfully", mapToProjection(savedRecord));
    }

    private ComplianceProjection mapToProjection(ComplianceRecord record) {
        return new ComplianceProjection(
                record.getComplianceId(),
                record.getEntityId(),
                record.getType(),
                record.getResult(),
                record.getDate(),
                record.getNotes()
        );
    }
}
