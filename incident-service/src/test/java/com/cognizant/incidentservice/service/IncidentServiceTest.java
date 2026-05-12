package com.cognizant.incidentservice.service;

import com.cognizant.incidentservice.classexception.IncidentException;
import com.cognizant.incidentservice.dto.IncidentDto;
import com.cognizant.incidentservice.entity.Incident;
import com.cognizant.incidentservice.projection.IncidentProjection;
import com.cognizant.incidentservice.projection.SuccessResponseProjection;
import com.cognizant.incidentservice.proxy.UserProxy;
import com.cognizant.incidentservice.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private UserProxy userProxy;

    @InjectMocks
    private IncidentServiceImpl incidentService;

    @Test
    public void getAllIncidents_ValidTest() {
        IncidentProjection p1 = new IncidentProjection(1L, 101L, "Fire", "Lab", LocalDate.now(), "PENDING");
        when(incidentRepository.getAllIncidents()).thenReturn(Arrays.asList(p1));

        SuccessResponseProjection<List<IncidentProjection>> response = incidentService.getAllIncidents();

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        verify(incidentRepository, times(1)).getAllIncidents();
    }

    @Test
    public void getUserIncidents_ValidTest() {
        Long userId = 101L;
        IncidentProjection p1 = new IncidentProjection(1L, userId, "Bullying", "Gym", LocalDate.now(), "PENDING");
        when(incidentRepository.getUserIncidents(userId)).thenReturn(Arrays.asList(p1));

        SuccessResponseProjection<List<IncidentProjection>> response = incidentService.getUserIncidents(userId);

        assertTrue(response.isSuccess());
        assertEquals(userId, response.getData().get(0).getReporter());
    }

    @Test
    public void getUserIncidents_EmptyList_ThrowsException() {
        Long userId = 999L;
        when(incidentRepository.getUserIncidents(userId)).thenReturn(new ArrayList<>());

        IncidentException exception = assertThrows(IncidentException.class, () -> {
            incidentService.getUserIncidents(userId);
        });

        assertEquals("No user incidents found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    public void createIncident_ValidTest() {
        IncidentDto dto = new IncidentDto();
        dto.setUserId(101L);
        dto.setType("Accident");
        dto.setLocation("Cafeteria");
        dto.setStatus("PENDING");

        // Mocking Feign Proxy Response
        SuccessResponseProjection<Boolean> proxyResponse = new SuccessResponseProjection<>(true, "User exists", true);
        when(userProxy.checkUserExists(101L)).thenReturn(new ResponseEntity<>(proxyResponse, HttpStatus.OK));

        Incident savedIncident = new Incident();
        savedIncident.setIncidentId(1L);
        savedIncident.setReporterId(101L);
        savedIncident.setType("Accident");

        when(incidentRepository.save(any(Incident.class))).thenReturn(savedIncident);

        SuccessResponseProjection<IncidentProjection> response = incidentService.createIncident(dto);

        assertTrue(response.isSuccess());
        assertEquals("Incident reported successfully", response.getMessage());
        assertEquals(1L, response.getData().getIncidentId());
    }

    @Test
    public void checkIncidentExists_True() {
        when(incidentRepository.existsById(1L)).thenReturn(true);

        SuccessResponseProjection<Boolean> response = incidentService.checkIncidentExists(1L);

        assertTrue(response.getData());
        verify(incidentRepository, times(1)).existsById(1L);
    }

    @Test
    public void updateIncidentStatus_ValidTest() {
        Long id = 1L;
        String newStatus = "RESOLVED";
        Incident incident = new Incident();
        incident.setIncidentId(id);
        incident.setStatus("PENDING");

        when(incidentRepository.findById(id)).thenReturn(Optional.of(incident));
        when(incidentRepository.save(any(Incident.class))).thenReturn(incident);

        SuccessResponseProjection<Long> response = incidentService.updateIncidentStatus(id, newStatus);

        assertEquals("Incident status updated successfully", response.getMessage());
    }

    @Test
    public void updateIncidentStatus_NotFound_ThrowsException() {
        Long id = 500L;
        when(incidentRepository.findById(id)).thenReturn(Optional.empty());

        IncidentException exception = assertThrows(IncidentException.class, () -> {
            incidentService.updateIncidentStatus(id, "RESOLVED");
        });

        assertTrue(exception.getMessage().contains("Incident not found"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}