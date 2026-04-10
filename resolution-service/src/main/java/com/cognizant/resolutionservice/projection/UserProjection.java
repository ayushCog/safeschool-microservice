package com.cognizant.resolutionservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProjection {
    private Long userId;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String status;
}
