package com.example.BackendPrestamos.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.BackendPrestamos.dto.UserDto;
import com.example.BackendPrestamos.dto.LoginDto;
import com.example.BackendPrestamos.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.BackendPrestamos.dto.EditarStatus;
import com.example.BackendPrestamos.service.PrestamoUserService;

@RestController
@RequestMapping("/user")
public class ControllerUser {

    private final UserService service;
    private final PrestamoUserService prestamoUserService;

    ControllerUser(UserService Userservice, PrestamoUserService prestamoUserService) {
        this.service = Userservice;
        this.prestamoUserService = prestamoUserService;
    }


    @PostMapping("/crear")
    public Map<String, Object> crearUsuario(@RequestBody UserDto userDto) {

        // Lógica para crear un préstamo
        Map<String, Object> response = new java.util.HashMap<>();
        response = service.createUser(userDto);
        return response;
    }
    @PostMapping("/login")
    public Map<String, Object> loginUsuario(@RequestBody LoginDto userDto) {
        // Lógica para crear un préstamo
        Map<String, Object> response = new java.util.HashMap<>();
        response = service.loginUser(userDto);
        return response;
    }
    @GetMapping("/BuscarTodos")
    public Map<String, Object> ObtenerTodosLosusuarios() {
        Map<String, Object> response = new java.util.HashMap<>();
        response = service.getUserByAll();
        return response;
    }

    @PatchMapping("/actualizar")
    public Map<String, Object> actualizarPrestamo(@RequestBody EditarStatus status) {
        // Este alias conserva compatibilidad con clientes que usan /user/actualizar.
        return prestamoUserService.updatePrestamoUser(status);
    }


}
