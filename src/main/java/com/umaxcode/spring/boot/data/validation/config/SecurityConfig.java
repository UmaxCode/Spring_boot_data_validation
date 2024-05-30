package com.umaxcode.spring.boot.data.validation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaxcode.spring.boot.data.validation.filters.JWTSecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JWTSecurityFilter jwtSecurityFilter;
    private final OAuth2LoginSuccessHandler authenticationSuccessHandler;

    @Value("${frontend.login.url}")
    private String frontedLoginURL;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/api/v1/auth/**", "/login/oauth2/code/**", "swagger-ui.html", "swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())

                .oauth2Login(oauth -> oauth
                        .successHandler(authenticationSuccessHandler)
                        .failureUrl(frontedLoginURL))

                .exceptionHandling(exception -> {

                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, String> error = new HashMap<>();

                    exception.authenticationEntryPoint((request, response, authException) -> {
                        error.put("error", authException.getMessage());
                        String json = objectMapper.writeValueAsString(error);
                        response.setStatus(401);
                        response.setContentType("application/json");
                        response.getWriter().write(json);
                    });
                    exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                        error.put("error", accessDeniedException.getMessage());
                        String json = objectMapper.writeValueAsString(error);
                        response.setStatus(403);
                        response.setContentType("application/json");
                        response.getWriter().write(json);
                    });
                })

                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;

    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }


}
