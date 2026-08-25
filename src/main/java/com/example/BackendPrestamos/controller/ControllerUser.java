package com.example.BackendPrestamos.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.BackendPrestamos.dto.UserDto;
import com.example.BackendPrestamos.service.UserService;

@RestController
@RequestMapping("/user")
public class ControllerUser {

    private final UserService service;
    ControllerUser(UserService Userservice) {
        this.service = Userservice;
    }


    @PostMapping("/crear")
    public Map<String, Object> crearUsuario(@RequestBody UserDto userDto) {

        // Lógica para crear un préstamo
        Map<String, Object> response = new java.util.HashMap<>();
        response = service.createUser(userDto);
        return response;
    }


}
