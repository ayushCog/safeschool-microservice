package com.cognizant.resolutionservice.proxy;

import com.cognizant.resolutionservice.projection.SuccessResponseProjection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "incident-service")
public interface IncidentProxy {
    @GetMapping("/incidents/exists/{id}")
    ResponseEntity<SuccessResponseProjection<Boolean>> checkIncidentExists(@PathVariable("id") Long id);

    @PostMapping("/incidents/update/{id}")
    ResponseEntity<SuccessResponseProjection<String>> updateIncidentStatus(@PathVariable("id") Long id, String status);
}
