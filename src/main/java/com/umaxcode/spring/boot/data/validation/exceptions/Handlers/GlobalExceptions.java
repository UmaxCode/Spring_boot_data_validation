package com.umaxcode.spring.boot.data.validation.exceptions.Handlers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> fieldValidationExceptionHandler(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach((constraintViolation) -> errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> DataBaseExceptionHandler(){
        Map<String, String> response = new HashMap<>();
        response.put("error", "username or email already exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



    @ExceptionHandler(value = {BadCredentialsException.class, AccessDeniedException.class, SignatureException.class, ExpiredJwtException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> securityExceptionHandler(Exception ex){
        Map<String, String>  response = new HashMap<>();
        HttpStatus status  = HttpStatus.FORBIDDEN;

        if(ex instanceof BadCredentialsException){
            response.put("error", "Invalid email or password");
            status = HttpStatus.UNAUTHORIZED;
        }else if(ex instanceof AccessDeniedException){
            response.put("error", "Access denied to this resources");
        }else if(ex instanceof SignatureException || ex instanceof MalformedJwtException){
            response.put("error", "Invalid JWT token");
        }else if(ex instanceof ExpiredJwtException) {
            response.put("error", "JWT token expired");
        }else if(ex instanceof HttpRequestMethodNotSupportedException){
            status = HttpStatus.METHOD_NOT_ALLOWED;
            response.put("error", ex.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

}
