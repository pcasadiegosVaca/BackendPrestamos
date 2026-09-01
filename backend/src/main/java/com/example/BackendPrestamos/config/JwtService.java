package com.example.BackendPrestamos.config;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    public static javax.crypto.SecretKey SECRET_KEY;

    public JwtService(@Value("${jwt.secret}") String secret) {
        SECRET_KEY = io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secret));
    }
    private static final long EXPIRATION_TIME = 86400000; 

    public String generarToken(String email,String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); 

        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }
    
}

