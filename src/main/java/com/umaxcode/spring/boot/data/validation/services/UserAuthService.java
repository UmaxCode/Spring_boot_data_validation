package com.umaxcode.spring.boot.data.validation.services;


import com.umaxcode.spring.boot.data.validation.dtos.requests.UserAuthenticationDTO;
import com.umaxcode.spring.boot.data.validation.dtos.requests.UserCreationDTO;
import com.umaxcode.spring.boot.data.validation.dtos.responses.AuthenticationSuccessDTO;
import com.umaxcode.spring.boot.data.validation.entities.RefreshToken;
import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.enums.Role;
import com.umaxcode.spring.boot.data.validation.validations.FieldsValidations;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final JWTAuthenticationService jwtAuthenticationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final FieldsValidations<Object> fieldsValidations;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;


    public User createUser(UserCreationDTO data) {

        fieldsValidations.validate(data);

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(data.email())
                .username(data.username())
                .password(passwordEncoder.encode(data.password()))
                .role(Role.USER)
                .isActive(false)
                .build();;

        return userService.addUser(user);
    }


    @Transactional
    public AuthenticationSuccessDTO authenticateUser(UserAuthenticationDTO data) {

        fieldsValidations.validate(data);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.email(), data.password()));

        User user = userService.getUserByEmail(data.email());

        String token = jwtAuthenticationService.generateToken(user.getEmail(), user.getId());

        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user);

        return new AuthenticationSuccessDTO(user.getUserDetails(), token, refreshToken.getToken());

    }


}
