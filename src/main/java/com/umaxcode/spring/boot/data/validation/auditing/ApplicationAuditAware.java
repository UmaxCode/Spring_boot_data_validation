package com.umaxcode.spring.boot.data.validation.auditing;

import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.entities.UserDetailsImpl.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
             return Optional.empty();
        }

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        return Optional.of(user.getUsername());
    }
}
