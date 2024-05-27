package com.umaxcode.spring.boot.data.validation.exceptions.custom;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

    private final HttpStatus status;

    CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
