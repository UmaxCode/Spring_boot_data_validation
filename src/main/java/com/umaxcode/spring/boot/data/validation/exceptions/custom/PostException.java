package com.umaxcode.spring.boot.data.validation.exceptions.custom;

import org.springframework.http.HttpStatus;


public class PostException extends CustomException {

    public PostException(HttpStatus status, String message) {
        super(status, message);
    }
}
