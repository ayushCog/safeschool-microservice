package com.cognizant.resolutionservice.globalexception;

import com.cognizant.resolutionservice.projection.ErrorResponseProjection;
import com.cognizant.resolutionservice.classexception.ResolutionException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResolutionException.class)
    public ResponseEntity<?> handleResolutionException(ResolutionException ex){
        ErrorResponseProjection errorResponse=new ErrorResponseProjection(false, ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    /*@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseProjection> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseProjection(false, "No permission to perform this action"));
    }*/

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponseProjection> handleFeignException(FeignException ex) {
        String content = ex.contentUTF8();

        String extractedMessage = "External service error";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(content);

            if (node.has("message")) {
                extractedMessage = node.get("message").asText();
            }
        } catch (Exception e) {
            extractedMessage = (content != null && !content.isEmpty()) ? content : ex.getMessage();
        }

        return ResponseEntity
                .status(ex.status() != -1 ? ex.status() : 500)
                .body(new ErrorResponseProjection(false, extractedMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseProjection> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseProjection(false, "Required fields must be given"));
    }
}
