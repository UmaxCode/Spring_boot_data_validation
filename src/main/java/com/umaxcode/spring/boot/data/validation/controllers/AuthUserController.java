package com.umaxcode.spring.boot.data.validation.controllers;


import com.umaxcode.spring.boot.data.validation.dtos.requests.RefreshTokenRequest;
import com.umaxcode.spring.boot.data.validation.dtos.requests.UserAuthenticationDTO;
import com.umaxcode.spring.boot.data.validation.dtos.requests.UserCreationDTO;
import com.umaxcode.spring.boot.data.validation.exceptions.custom.UserAuthException;
import com.umaxcode.spring.boot.data.validation.services.RefreshTokenService;
import com.umaxcode.spring.boot.data.validation.services.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthUserController {

    private final UserAuthService userAuthService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserCreationDTO request) {

        var response = userAuthService.createUser(request).getUserDetails();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthenticationDTO request) {

        var response = userAuthService.authenticateUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        var response = refreshTokenService.generateNewTokenFromRefreshToken(request.token());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {


        return ResponseEntity.status(HttpStatus.OK).body("Welcome!" );
    }


    @ExceptionHandler(UserAuthException.class)
    public ResponseEntity<?> handleException(UserAuthException error) {
        return ResponseEntity.status(error.getStatus()).body(error.getMessage());
    }

}
