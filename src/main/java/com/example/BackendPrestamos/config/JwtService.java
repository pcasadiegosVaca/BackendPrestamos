package com.example.BackendPrestamos.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service // <-- Esta etiqueta hace que Spring reconozca la clase
public class JwtService {

    // Genera una llave segura temporal para firmar tus tokens
// Cambia Key por SecretKey (Asegúrate de importar javax.crypto.SecretKey)
public static final javax.crypto.SecretKey SECRET_KEY = io.jsonwebtoken.security.Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    // Tiempo de vida del token: 1 día en milisegundos
    private static final long EXPIRATION_TIME = 86400000; 

    public String generarToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }
    
}

