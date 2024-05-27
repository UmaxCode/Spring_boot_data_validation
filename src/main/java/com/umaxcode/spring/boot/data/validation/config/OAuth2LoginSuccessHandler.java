package com.umaxcode.spring.boot.data.validation.config;

import com.umaxcode.spring.boot.data.validation.dtos.responses.AuthenticationSuccessDTO;
import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.services.JWTAuthenticationService;
import com.umaxcode.spring.boot.data.validation.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final JWTAuthenticationService jwtAuthenticationService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        this.setAlwaysUseDefaultTargetUrl(true);
        var userRegistrationResponse = registerUser(authentication);
        this.setDefaultTargetUrl(frontendUrl);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(userRegistrationResponse.toString());
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }


    public AuthenticationSuccessDTO registerUser(Authentication authentication) {

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication;

        String email = oAuth2User.getAttributes().get("email").toString();
        String firstName = oAuth2User.getAttributes().get("family_name").toString();
        String lastName = oAuth2User.getAttributes().get("given_name").toString();
        String username = oAuth2User.getAttributes().get("username").toString();

        log.info("Registering user {}", authentication.getName());

        User user;
        String token;

        try {
            user = userService.getUserByEmail(email);
        } catch (Exception ex) {
            user = userService.addUser(User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .build());
        }

        token = jwtAuthenticationService.generateToken(user.getEmail(), user.getId().toString());
        return new AuthenticationSuccessDTO(user.getUserDetails(), token, null);
    }
}
