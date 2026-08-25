package com.example.BackendPrestamos;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // 1. Deshabilitar CSRF porque usaremos tokens JWT (las APIs REST no sufren de CSRF)
            .csrf(csrf -> csrf.disable())
            
            // 2. Hacer que la aplicación no guarde sesiones en el servidor (Stateless)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 3. Configurar las reglas de tus URLs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/registrar", "/auth/login").permitAll() // <-- Cualquiera puede registrarse o loguearse
                .anyRequest().authenticated() // <-- Todo lo demás (como /prestamos/crear) queda bloqueado automáticamente
            )
            .build();
    }

    // 4. Bean para encriptar las contraseñas de los usuarios en la Base de Datos de forma segura
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
