package com.umaxcode.spring.boot.data.validation.config.providers;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAuthenticationProviders {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationProvider getDaoAuthenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    public AuthenticationProvider getLdapAuthenticationProvider(){
        return null;
    }
}
