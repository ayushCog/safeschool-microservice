package com.cognizant.userservice.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class StudentProjection {
    private Long userId;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String status;

    private String gender;
    private String address;
    private LocalDate dob;
}
