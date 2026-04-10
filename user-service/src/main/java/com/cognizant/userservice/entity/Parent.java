package com.cognizant.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Parent must be linked to a User account")
    private User user;

    @NotBlank(message = "Relationship type is required")
    private String relation;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull(message = "Parent must be associated with a Student")
    private Student student;
}