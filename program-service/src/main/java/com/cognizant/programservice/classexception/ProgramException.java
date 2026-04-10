package com.cognizant.programservice.classexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProgramException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ProgramException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
