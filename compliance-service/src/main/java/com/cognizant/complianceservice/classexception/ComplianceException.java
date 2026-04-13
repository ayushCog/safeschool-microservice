package com.cognizant.complianceservice.classexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ComplianceException extends RuntimeException{
    private final HttpStatus httpStatus;

    public ComplianceException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
