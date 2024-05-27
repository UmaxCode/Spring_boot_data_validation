package com.umaxcode.spring.boot.data.validation.exceptions.custom;

import org.springframework.http.HttpStatus;

public class UserAuthException extends CustomException{

    public UserAuthException(HttpStatus status, String message) {
        super(status, message);
    }
}
