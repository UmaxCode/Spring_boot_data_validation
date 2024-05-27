package com.umaxcode.spring.boot.data.validation.services;

import com.umaxcode.spring.boot.data.validation.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JWTAuthenticationService {

    @Value("${jwt.security.key}")
    private String signingKey;

    @Value("${jwt.security.expiration}")
    private int expirationTime;


    public boolean isTokenValid(String token) {

        return !isTokenExpired(token) && isValidIssuer(token);
    }


    public String generateToken(String email, String userId) {

        HashMap<String, String> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", Role.USER.name());

        return Jwts.builder()
                .claims(claims)
                .issuer("umaxcode")
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }


    public String getUserEmailFromToken(String token) {
        return extractClaim(token, claims -> claims.get("email")).toString();
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, claims -> claims.getExpiration().before(new Date()));
    }

    private boolean isValidIssuer(String token) {
        return extractClaim(token, claims -> claims.getIssuer().equals("umaxcode"));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
