package com.umaxcode.spring.boot.data.validation.config;

import com.umaxcode.spring.boot.data.validation.auditing.ApplicationAuditAware;
import com.umaxcode.spring.boot.data.validation.mappers.UserMapper;
import com.umaxcode.spring.boot.data.validation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserService userService;
    private final UserMapper userMapper;


    @Bean
    public UserDetailsService userDetailsService() {

        return username -> userMapper.toUserDetails(userService.getUserByEmail(username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuditorAware<String> auditorAware(){
        return new ApplicationAuditAware();
    }
}
