package com.finace.AuthService.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;


@Service
public class JwtService {
    private final Key signKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        System.out.println("INJECTED SECRET VALUE: " + secret); // 🔍 debug log

        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret is not configured. Please add 'jwt.secret' in application.yml");
        }

        try {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            this.signKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid JWT secret key format", e);
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(signKey)
                .compact();
    }


    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
