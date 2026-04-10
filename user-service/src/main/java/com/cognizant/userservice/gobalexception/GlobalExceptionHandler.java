package com.cognizant.userservice.gobalexception;

import com.cognizant.userservice.classexception.*;
import com.cognizant.userservice.classexception.SecurityException;
import com.cognizant.userservice.projection.ErrorResponseProjection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException ex){
        ErrorResponseProjection errorResponse=new ErrorResponseProjection(false, ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<?> handleStudentException(StudentException ex){
        ErrorResponseProjection errorResponse=new ErrorResponseProjection(false, ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> handleSecurityException(SecurityException ex){
        ErrorResponseProjection errorResponse=new ErrorResponseProjection(false, ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseProjection> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseProjection(false, "No permission to perform this action"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseProjection> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseProjection(false, "Required fields must be given"));
    }
}
