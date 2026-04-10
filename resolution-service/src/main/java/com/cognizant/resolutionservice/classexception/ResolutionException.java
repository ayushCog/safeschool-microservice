package com.cognizant.resolutionservice.classexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResolutionException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ResolutionException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
