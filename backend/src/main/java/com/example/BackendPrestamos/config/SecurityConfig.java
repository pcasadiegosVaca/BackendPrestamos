package com.example.BackendPrestamos.config;

// 1. Asegúrate de importar tu filtro personalizado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 2. Inyectamos el filtro de JWT que creamos en los pasos anteriores
    @Autowired
    private FiltrosDeautenticacion jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/user/crear").permitAll() // Público
                .requestMatchers("/user/login").permitAll() // Público
                .requestMatchers("/prestamos/crear").authenticated() // Requiere token
                .requestMatchers("/prestamos/actualizar").authenticated() // Requiere token BuscarTodos
                .requestMatchers("/prestamos/ObtenerTodosPrestamos").authenticated() // Requiere token BuscarTodos
                .requestMatchers("/user/BuscarTodos").permitAll() // Requiere token BuscarTodos
                .requestMatchers("/prestamos/buscarpretamos/{id}").authenticated() // Requiere token BuscarTodos
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
                // 2. Desactivar CSRF específicamente para H2 (H2 lo necesita para enviar formularios)
            //.csrf(csrf -> csrf
             //   .ignoringRequestMatchers("/h2-console/**")
            //)
            // 3. Permitir que la consola de H2 se cargue dentro de los iframes del navegador
            .headers(headers -> headers
               .frameOptions(frameOptions -> frameOptions.sameOrigin())
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
