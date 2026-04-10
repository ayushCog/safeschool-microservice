package com.cognizant.authservice.util;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenUtil {
    private final String SECRET_KEY = "secretkeyforsafeschoolsecretkeythissecretkeyforsafeschoolsecretkeythis";

    public String generateToken(String email, String role) {
        role="ROLE_" + role.toUpperCase();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
