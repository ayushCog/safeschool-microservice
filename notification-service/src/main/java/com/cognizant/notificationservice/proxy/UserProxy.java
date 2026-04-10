package com.cognizant.notificationservice.proxy;



import com.cognizant.notificationservice.projection.SuccessResponseProjection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "user-service")
public interface UserProxy {
    @GetMapping("/user/all-ids")
    ResponseEntity<SuccessResponseProjection<List<Long>>> getAllUserIds();

    @GetMapping("/user/all-ids/role/{role}")
    ResponseEntity<SuccessResponseProjection<List<Long>>> getUserIdsByRole(@PathVariable("role") String role);
}
