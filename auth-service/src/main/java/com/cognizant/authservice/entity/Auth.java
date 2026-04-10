package com.cognizant.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Auth {
    @Id
    @NotBlank(message = "Email is required")
    @Email
    private String emailId;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Role is required")
    String role;
}
