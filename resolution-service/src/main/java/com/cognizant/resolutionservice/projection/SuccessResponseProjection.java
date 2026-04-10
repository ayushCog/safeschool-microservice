package com.cognizant.resolutionservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponseProjection<T> {
    private boolean success;
    private String message;
    private T data;
}
