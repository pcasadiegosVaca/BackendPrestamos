package com.example.BackendPrestamos.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import com.example.BackendPrestamos.repository.UserRegistroRepository;
import com.example.BackendPrestamos.dto.UserDto;
import com.example.BackendPrestamos.entity.UserRegistro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    public Map<String, Object> saveUser(UserDto userDto) {
        UserRegistro userEntity = new UserRegistro();
        userEntity.setNombre(userDto.getNombre());
        userEntity.setApellido(userDto.getApellido());
        userEntity.setCorreo(userDto.getCorreo());
        // Cifrar la contraseña antes de guardarla
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userEntity.setPassword(encodedPassword);
        repository.save(userEntity);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuario creado exitosamente desde el service");
        return response;
    }
  

}
