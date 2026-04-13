package com.cognizant.complianceservice.proxy;



import com.cognizant.complianceservice.config.FeignConfig;
import com.cognizant.complianceservice.projection.SuccessResponseProjection;
import com.cognizant.complianceservice.projection.UserProjection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserProxy {

    @GetMapping("/user/{id}")
    ResponseEntity<SuccessResponseProjection<UserProjection>> getUserById(@PathVariable("id") Long id);

    @GetMapping("/user/exist/{id}")
    ResponseEntity<SuccessResponseProjection<Boolean>> checkUserExists(@PathVariable("id") Long id);
}
