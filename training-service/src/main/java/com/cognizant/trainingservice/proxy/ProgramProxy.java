package com.cognizant.trainingservice.proxy;

import com.cognizant.trainingservice.projection.ProgramProjection;
import com.cognizant.trainingservice.projection.SuccessResponseProjection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "program-service")
public interface ProgramProxy {
    @GetMapping("/program/exist/{id}")
    ResponseEntity<SuccessResponseProjection<Boolean>> checkProgramExists(@PathVariable("id") Long id);

    @GetMapping("/program/{programId}")
    ResponseEntity<SuccessResponseProjection<ProgramProjection>> get(@PathVariable("programId") Long programId);
}
