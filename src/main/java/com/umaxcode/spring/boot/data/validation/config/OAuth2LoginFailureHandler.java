package com.umaxcode.spring.boot.data.validation.config;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
}
