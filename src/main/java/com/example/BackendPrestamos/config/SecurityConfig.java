package com.example.BackendPrestamos.config;

// 1. Asegúrate de importar tu filtro personalizado
import com.example.BackendPrestamos.config.FiltrosDeautenticacion; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // <-- IMPORTANTE

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 2. Inyectamos el filtro de JWT que creamos en los pasos anteriores
    @Autowired
    private FiltrosDeautenticacion jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/crear").permitAll() // Público
                .requestMatchers("/user/login").permitAll() // Público
                .requestMatchers("/prestamos/crear").authenticated() // Requiere token
                .requestMatchers("/prestamos/actualizar").authenticated() // Requiere token BuscarTodos
                .requestMatchers("/user/actualizar").authenticated() // Alias de actualizacion para Postman
                .requestMatchers("/user/BuscarTodos").permitAll() // Requiere token BuscarTodos

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // 3. ¡ESTA ES LA LÍNEA CLAVE QUE FALTA!
            // Le dice a Spring: "Antes de bloquear al usuario, pasa la petición por mi filtro de JWT"
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
