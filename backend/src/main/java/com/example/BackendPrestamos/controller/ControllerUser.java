package com.example.BackendPrestamos.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendPrestamos.dto.LoginDto;
import com.example.BackendPrestamos.dto.UserDto;
import com.example.BackendPrestamos.error.ErrorHttp;
import com.example.BackendPrestamos.service.UserService;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins="http://localhost:4200")
public class ControllerUser {

    private final UserService service;

    ControllerUser(UserService Userservice) {
        this.service = Userservice;
    }


    @PostMapping("/crear")
    public Map<String, Object> crearUsuario(@RequestBody UserDto userDto) {

        try {
            Map<String, Object> response = service.createUser(userDto);
            response.put("message", "Usuario creado exitosamente");
            return response;
        } catch (Exception e) {
            return ErrorHttp.mapearError(e).getBody();
        }

    }
    @PostMapping("/login")
    public Map<String, Object> loginUsuario(@RequestBody LoginDto userDto) {
       try {
            Map<String, Object> response = service.loginUser(userDto);
            response.put("message", "Usuario logueado exitosamente");
            return response;
        } catch (Exception e) {
            return ErrorHttp.mapearError(e).getBody();
        }
    }
    @GetMapping("/BuscarTodos")
    public Map<String, Object> ObtenerTodosLosusuarios() {
        try {
            Map<String, Object> response = service.getUserByAll();
            response.put("message", "Usuarios obtenidos exitosamente");
            return response;
        } catch (Exception e) {
            return ErrorHttp.mapearError(e).getBody();
        }
    }





}
