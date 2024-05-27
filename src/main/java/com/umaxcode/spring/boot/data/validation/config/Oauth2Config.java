package com.umaxcode.spring.boot.data.validation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class Oauth2Config {

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

    public ClientRegistration facebookClientRegistration() {

        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook")
                .clientId("DFDFDFDF")
                .clientSecret("DFDFDD")
                .build();
    }

    public ClientRegistration googleClientRegistration() {
        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId("DFDFDFDF")
                .clientSecret("DFDFDFFD")
                .build();
    }

}
