package com.cognizant.resolutionservice.proxy;

import com.cognizant.resolutionservice.config.FeignConfig;
import com.cognizant.resolutionservice.projection.SuccessResponseProjection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserProxy {
    @GetMapping("user/exist/{id}")
    ResponseEntity<SuccessResponseProjection<Boolean>> checkUserExists(@PathVariable("id") Long id);
}
