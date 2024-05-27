package com.umaxcode.spring.boot.data.validation.filters;

import com.umaxcode.spring.boot.data.validation.services.JWTAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Component
@RequiredArgsConstructor
public class JWTSecurityFilter extends OncePerRequestFilter {


    private final JWTAuthenticationService jwtAuthenticationService;

    private final UserDetailsService userDetailsService;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) {

        String authorization = request.getHeader("Authorization");
        String jwtToken;

       try {

           if (authorization != null && authorization.startsWith("Bearer ")) {
               jwtToken = authorization.substring(7);

               if(jwtAuthenticationService.isTokenValid(jwtToken)){

                   String email =  jwtAuthenticationService.getUserEmailFromToken(jwtToken);

                   UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                   UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                   authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                   SecurityContextHolder.getContext().setAuthentication(authentication);

               }

           }

           filterChain.doFilter(request, response);

       }catch (Exception ex){
           handlerExceptionResolver.resolveException(request, response, null, ex);
       }

    }
}
