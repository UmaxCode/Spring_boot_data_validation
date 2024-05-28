package com.umaxcode.spring.boot.data.validation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class Oauth2Config {

    @Value("${application.security.google.oauth.clientId}")
    private String clientId;

    @Value("${application.security.google.oauth.clientSecret}")
    private String clientSecret;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(githubClientRegistration(), facebookClientRegistration(), googleClientRegistration());
    }


    public ClientRegistration githubClientRegistration() {

        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("FDFD")
                .clientSecret("DFDFDDFDF")
                .build();
    }

    @Bean
    public ClientRegistration facebookClientRegistration() {

        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook")
                .clientId("DFDFDFDF")
                .clientSecret("DFDFDD")
                .build();
    }

    public ClientRegistration googleClientRegistration() {
        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri("{baseUrl}/login/oauth2/code/google")
                .scope("profile", "email")
                .build();
    }

}
