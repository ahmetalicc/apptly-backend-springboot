package com.example.apptly.backend.springboot.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {
    @Value("${apptly.security.jwt.secret}")
    private String SECRET_KEY;
    @Value("${apptly.security.jwt.expire}")
    private Long JWT_EXPIRE_TIME;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private JwtParser jwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String generateToken(String email, String role, Long tenantId) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("tenantId", tenantId);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(email)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return jwtParser().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            jwtParser().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return jwtParser().parseClaimsJws(token).getBody();
    }
}
