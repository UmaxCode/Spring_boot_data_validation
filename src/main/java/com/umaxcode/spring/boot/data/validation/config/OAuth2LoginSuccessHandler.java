package com.umaxcode.spring.boot.data.validation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.enums.Role;
import com.umaxcode.spring.boot.data.validation.services.JWTAuthenticationService;
import com.umaxcode.spring.boot.data.validation.services.RefreshTokenService;
import com.umaxcode.spring.boot.data.validation.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JWTAuthenticationService jwtAuthenticationService;
    private final RefreshTokenService refreshTokenService;


    @Value("${frontend.home.url}")
    private String frontendHomeURL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        OAuth2User oauthUser = ((OAuth2AuthenticationToken) authentication).getPrincipal();

        String userId = UUID.randomUUID().toString();
        String email = oauthUser.getAttribute("email");
        String firstName = oauthUser.getAttribute("given_name");
        String lastName = oauthUser.getAttribute("family_name");

        User user;
        String token;
        ObjectMapper objectMapper = new ObjectMapper();


        try {

            user = userService.getUserByEmail(email);

        } catch (Exception ex) {
            user = User.builder()
                    .id(userId)
                    .email(email)
                    .username(firstName + userId.split("-")[0])
                    .firstName(firstName)
                    .lastName(lastName)
                    .isActive(true)
                    .role(Role.USER)
                    .build();

            userService.addUser(user);

        }

        token = jwtAuthenticationService.generateToken(user.getEmail(), user.getId());
        String refreshToken = refreshTokenService.generateRefreshToken(user).getToken();
        String userInfo = objectMapper.writeValueAsString(user.getUserDetails());
        String encodedUserInfo = URLEncoder.encode(userInfo, StandardCharsets.UTF_8);
        response.sendRedirect(frontendHomeURL+String.format("?token=%s&refreshToken=%s&userInfo=%s", token, refreshToken, encodedUserInfo));
    }

}
