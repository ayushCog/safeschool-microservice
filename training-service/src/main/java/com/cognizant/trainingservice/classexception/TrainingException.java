package com.cognizant.trainingservice.classexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TrainingException extends RuntimeException {
    private final HttpStatus httpStatus;

    public TrainingException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
