package com.cognizant.complianceservice.globalexception;

import com.cognizant.complianceservice.classexception.ComplianceException;
import com.cognizant.complianceservice.projection.ErrorResponseProjection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ComplianceException.class)
    public ResponseEntity<?> handleIncidentException(ComplianceException ex){
        ErrorResponseProjection errorResponse=new ErrorResponseProjection(false, ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    /*@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseProjection> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseProjection(false, "No permission to perform this action"));
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseProjection> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseProjection(false, "Required fields must be given"));
    }
}
