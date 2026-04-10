package com.cognizant.authservice.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ParentProjection {
    private String name;
    private String role;
    private String email;
    private String phone;
    private String status;

    private String relation;
    private String studentEmail;
}
