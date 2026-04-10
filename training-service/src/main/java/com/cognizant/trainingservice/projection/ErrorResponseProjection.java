package com.cognizant.trainingservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseProjection {
    private boolean success;
    private String message;
}
