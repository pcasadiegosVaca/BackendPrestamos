package com.example.BackendPrestamos.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendPrestamos.dto.EditarStatus;
import com.example.BackendPrestamos.dto.PrestamoDto;
import com.example.BackendPrestamos.error.ErrorHttp;
import com.example.BackendPrestamos.service.PrestamoUserService;
@RestController
@RequestMapping("/prestamos")
@CrossOrigin(origins="http://localhost:4200")
public class PresamosController {
    private final PrestamoUserService service;

    public PresamosController(PrestamoUserService prestamoUserService) {
        this.service = prestamoUserService;
    }

    @PostMapping("/crear")
    public Map<String, Object> crearPrestamo(@RequestBody PrestamoDto prestamoDto) {
        try {
            Map<String, Object> response = service.savePrestamoUser(prestamoDto);
            response.put("message", "Prestamo creado exitosamente");
            return response;
        } catch (Exception e) {
            return ErrorHttp.mapearError(e).getBody();
        }
       
    }

    @PatchMapping("/actualizar")
    public Map<String, Object> actualizarPrestamo(@RequestBody EditarStatus status) {
        try {
            return service.updatePrestamoUser(status);
        } catch (Exception e) {
            return ErrorHttp.mapearError(e).getBody();
        }
    }
    
    @GetMapping("/buscarpretamos/{id}")
    public Map<String, Object> buscarPrestasmo(@PathVariable Long id) {
        try {
            return service.buscarPrestamoUser(id);
        } catch (Exception e) {
            return ErrorHttp.mapearError(e).getBody();
        }
    }
    @GetMapping("/ObtenerTodosPrestamos")
    public Map<String, Object> ObtenerTodosLosPrestamos() {
        try {
            Map<String, Object> response = service.getPrestamoByAll();
            response.put("message", "Usuarios obtenidos exitosamente");
            return response;
        } catch (Exception e) {
            return ErrorHttp.mapearError(e).getBody();
        }
    }

}
