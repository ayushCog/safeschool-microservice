package com.cognizant.authservice.proxy;

import com.cognizant.authservice.dto.ParentRegistrationDto;
import com.cognizant.authservice.dto.StudentRegistrationDto;
import com.cognizant.authservice.dto.UserRegistrationDto;
import com.cognizant.authservice.projection.ParentProjection;
import com.cognizant.authservice.projection.StudentProjection;
import com.cognizant.authservice.projection.SuccessResponseProjection;
import com.cognizant.authservice.projection.UserProjection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="user-service")
public interface UserProxy {
    @PostMapping("/user/register")
    public ResponseEntity<SuccessResponseProjection<UserProjection>> registerUser(UserRegistrationDto userRegistrationDto);

    @PostMapping("/student/register")
    public ResponseEntity<SuccessResponseProjection<StudentProjection>> registerStudent(StudentRegistrationDto studentRegistrationDto);

    @PostMapping("/parent/register")
    public ResponseEntity<SuccessResponseProjection<ParentProjection>> registerParent(ParentRegistrationDto parentRegistrationDto);
}
