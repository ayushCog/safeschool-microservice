package com.cognizant.trainingservice.globalexception;

import com.cognizant.trainingservice.projection.ErrorResponseProjection;
import com.cognizant.trainingservice.classexception.TrainingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TrainingException.class)
    public ResponseEntity<?> handleTrainingException(TrainingException ex){
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
