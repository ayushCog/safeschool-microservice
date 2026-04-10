package com.cognizant.userservice.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponseProjection<T> {
    boolean success;
    String message;
    T data;
}
