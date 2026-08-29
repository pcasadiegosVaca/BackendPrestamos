package com.example.BackendPrestamos.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BackendPrestamos.config.JwtService;
import com.example.BackendPrestamos.dto.LoginDto;
import com.example.BackendPrestamos.dto.UserDto;
import com.example.BackendPrestamos.entity.UserRegistro;
import com.example.BackendPrestamos.repository.UserRegistroRepository;
@Service
public class UserService {
    @Autowired
    private UserRegistroRepository repository;
    private final PasswordEncoder passwordEncoder; // <--- Provisto por Spring Security
    private final JwtService jwtService;

    public UserService(UserRegistroRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Map<String, Object> createUser(UserDto userDto) {
        UserRegistro userEntity = new UserRegistro();
        userEntity.setNombre(userDto.getNombre());
        userEntity.setApellido(userDto.getApellido());
        userEntity.setCorreo(userDto.getCorreo());
        if( userDto.getRole().equals("USER")) {
            new IllegalArgumentException("El role debe ser 'USER' o 'ADMIN'");
        }
        userEntity.setRole(userDto.getRole());
        // Cifrar la contraseña antes de guardarla
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userEntity.setPassword(encodedPassword);
        repository.save(userEntity);
        
        String token = jwtService.generarToken(userDto.getCorreo(),userDto.getRole()); // Genera un token JWT para el usuario recién creado
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuario creado exitosamente desde el service");
        response.put("token", token);
        return response;
    }

    public Map<String, Object> loginUser( LoginDto loginDto) {
        Map<String, Object> response = new HashMap<>();

// Opción B (Recomendada): Si no lo encuentra, lanza una excepción que frena el proceso
        UserRegistro usuario = repository.findByCorreo(loginDto.getCorreo())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el correo: " + loginDto.getCorreo())); // <-- Verifica que cierre con );

        if (usuario == null) {
            response.put("error", "Usuario no encontrado");
            return response;
        }
        // Verificar la contraseña
        if (!passwordEncoder.matches(loginDto.getPassword(), usuario.getPassword())) {
            response.put("error", "Contraseña incorrecta");
            return response;
        }
        // Generar un token JWT para el usuario autenticado
        String token = jwtService.generarToken(usuario.getCorreo(),usuario.getRole());
        response.put("message", "Login exitoso");
        response.put("user", usuario.getCorreo());
        response.put("role", usuario.getRole());
        response.put("id", usuario.getId());

        response.put("token", token);

        return response;
    }
    public Map<String, Object> getUserByAll() {
        Map<String, Object> response = new HashMap<>();
        UserRegistro [] usuarios = repository.findAll().toArray(new UserRegistro[0]);
        response.put("users", usuarios);
 

        return response;
    }
 
}
