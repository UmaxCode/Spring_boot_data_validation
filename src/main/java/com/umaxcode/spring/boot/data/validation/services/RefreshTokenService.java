package com.umaxcode.spring.boot.data.validation.services;


import com.umaxcode.spring.boot.data.validation.entities.RefreshToken;
import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.exceptions.custom.UserAuthException;
import com.umaxcode.spring.boot.data.validation.repositories.RefreshTokenRepository;
import com.umaxcode.spring.boot.data.validation.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTAuthenticationService jwtAuthenticationService;
    private final UserRepository userRepository;

    @Value("${application.jwt.refreshToken.expires}")
    private String refreshTokenExpirationTime;

    public Map<String, String> generateNewTokenFromRefreshToken(String token) {

        Map<String, String> response = new HashMap<>();

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByToken(token);

        if (refreshTokenOptional.isEmpty()) {
            throw new UserAuthException(HttpStatus.BAD_REQUEST, "Invalid Refresh Token");
        }

        var refreshToken = refreshTokenOptional.get();

        if (isRefreshTokenExpired(refreshToken)) {
            refreshTokenRepository.delete(refreshToken);
            throw new UserAuthException(HttpStatus.FORBIDDEN, "Expired Refresh Token. Please Sign in again.");
        }

        User user = refreshToken.getUser();
        response.put("token", jwtAuthenticationService.generateToken(user.getEmail(), user.getId()));

        return response;
    }

    private boolean isRefreshTokenExpired(RefreshToken refreshToken) {

        return refreshToken.getExpires().before(new Date());
    }


    @Transactional
    public RefreshToken generateRefreshToken(User user) {

        RefreshToken oldRefreshToken = user.getRefreshToken();

        if (oldRefreshToken != null) {
            user.setRefreshToken(null);
        }

        var generatedRefreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expires(new Date(System.currentTimeMillis() + Integer.parseInt(refreshTokenExpirationTime)))
                .user(user)
                .build();

        user.setRefreshToken(generatedRefreshToken);

        userRepository.save(user);

        return generatedRefreshToken;
    }


}
