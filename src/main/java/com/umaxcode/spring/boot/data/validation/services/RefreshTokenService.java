package com.umaxcode.spring.boot.data.validation.services;


import com.umaxcode.spring.boot.data.validation.entities.RefreshToken;
import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.exceptions.custom.UserAuthException;
import com.umaxcode.spring.boot.data.validation.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    @Value("${application.jwt.refreshToken.expires}")
    private String refreshTokenExpirationTime;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTAuthenticationService jwtAuthenticationService;


    public Map<String, String> generateNewTokenFromRefreshToken(String token) {

        Map<String, String> response = new HashMap<>();

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByToken(token);

        if(refreshTokenOptional.isEmpty()){
            throw new UserAuthException(HttpStatus.BAD_REQUEST,"Invalid Refresh Token");
        }

        var refreshToken = refreshTokenOptional.get();

        if(isRefreshTokenExpired(refreshToken)){
            refreshTokenRepository.delete(refreshToken);
            throw new UserAuthException(HttpStatus.FORBIDDEN,"Expired Refresh Token. Please Sign in again.");
        }


        User user = refreshToken.getOwner();
        response.put("token", jwtAuthenticationService.generateToken(user.getEmail(), user.getId()));

        return response;
    }

    private boolean isRefreshTokenExpired(RefreshToken refreshToken) {

        return refreshToken.getExpires().before(new Date());
    }



    public RefreshToken generateRefreshToken(User user) {

       var refreshToken =  RefreshToken.builder()
               .token(UUID.randomUUID().toString())
               .expires(new Date(System.currentTimeMillis() + Integer.parseInt(refreshTokenExpirationTime)))
               .owner(user)
               .build();

       return refreshTokenRepository.save(refreshToken);
    }





}
