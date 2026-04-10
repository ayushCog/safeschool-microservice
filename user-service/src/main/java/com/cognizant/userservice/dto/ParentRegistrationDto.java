package com.cognizant.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParentRegistrationDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phone;

    private String status = "ACTIVE";

    @NotBlank(message = "Relation is required (FATHER/MOTHER/GUARDIAN)")
    private String relation;

    @NotBlank(message = "Student email is required to link accounts")
    @Email(message = "Invalid student email format")
    private String studentEmail;
}
