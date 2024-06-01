package com.umaxcode.spring.boot.data.validation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaxcode.spring.boot.data.validation.enums.Role;
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
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

import static com.umaxcode.spring.boot.data.validation.enums.Permission.*;
import static com.umaxcode.spring.boot.data.validation.enums.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
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
//                        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), Role.MANAGER.name())
//                        .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.getPermission(), MANAGER_READ.getPermission())
//                        .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.getPermission(), MANAGER_CREATE.getPermission())
//                        .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.getPermission(), MANAGER_UPDATE.getPermission())
//                        .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.getPermission(), MANAGER_DELETE.getPermission())
////
//                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
//                        .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.getPermission())
//                        .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.getPermission())
//                        .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.getPermission())
//                        .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.getPermission())

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
                        error.put("error", accessDeniedException.getMessage() + ": Can't perform this operation");
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
