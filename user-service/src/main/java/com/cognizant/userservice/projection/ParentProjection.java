package com.cognizant.userservice.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ParentProjection {
    private Long userId;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String status;

    private String relation;
    private String studentEmail;
}
