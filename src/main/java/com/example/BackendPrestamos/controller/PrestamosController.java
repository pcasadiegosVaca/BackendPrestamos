package com.example.BackendPrestamos.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import com.example.BackendPrestamos.dto.EditarStatus;

import com.example.BackendPrestamos.dto.PrestamoDto;
import com.example.BackendPrestamos.service.PrestamoUserService;

@RestController
@RequestMapping("/prestamos")
public class PrestamosController {
    private final PrestamoUserService service;

    public PrestamosController(PrestamoUserService prestamoUserService) {
        this.service = prestamoUserService;
    }

    @PostMapping("/crear")
    public Map<String, Object> crearPrestamo(@RequestBody PrestamoDto prestamoDto) {
        // Lógica para crear un préstamo
       

        Map<String, Object> response =  new java.util.HashMap<>();
        response = service.savePrestamoUser(prestamoDto);
        
        return response;
    }
    @PatchMapping("/actualizar")
    public Map<String, Object> actualizarPrestamo(@RequestBody EditarStatus status) {
        // Lógica para actualizar un préstamo
        Map<String, Object> response = new java.util.HashMap<>();
        response = service.updatePrestamoUser(status);
        return response;
    }
}
