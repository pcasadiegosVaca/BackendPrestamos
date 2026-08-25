package com.example.BackendPrestamos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import com.example.BackendPrestamos.config.JwtService;

@Component
public class FiltrosDeautenticacion extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/user/crear");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
                        try {
                // Sintaxis moderna para JJWT 0.12.x+
                String email = Jwts.parser()            // 1. Iniciamos el parser
                        .verifyWith(JwtService.SECRET_KEY) // 2. Usamos verifyWith en lugar de setSigningKey
                        .build()                        // 3. Compilamos el validador (¡ESTO FALTABA!)
                        .parseSignedClaims(token)       // 4. Procesamos el token firmado
                        .getPayload()                   // 5. Obtenemos el cuerpo de datos
                        .getSubject();                  // 6. Extraemos el usuario/email

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                System.out.println("Error validando token: " + e.getMessage());
            }

        }

        filterChain.doFilter(request, response);
    }
}
