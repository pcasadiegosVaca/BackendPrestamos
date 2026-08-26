package com.example.BackendPrestamos.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;

import com.example.BackendPrestamos.dto.PrestamoDto;
import com.example.BackendPrestamos.dto.EditarStatus;
import com.example.BackendPrestamos.service.PrestamoUserService;
import com.example.BackendPrestamos.error.ErrorHttp;
@RestController
@RequestMapping("/prestamos")
public class PresamosController {
    private final PrestamoUserService service;

    public PresamosController(PrestamoUserService prestamoUserService) {
        this.service = prestamoUserService;
    }

    @PostMapping("/crear")
    public Map<String, Object> crearPrestamo(@RequestBody PrestamoDto prestamoDto) {
        // Lógica para crear un préstamo
        try {
            Map<String, Object> response = service.savePrestamoUser(prestamoDto);
            response.put("message", "Prestamo creado exitosamente");
            return response;
        } catch (Exception e) {
            // Manejo de errores y mapeo a una respuesta HTTP adecuada
            return ErrorHttp.mapearError(e).getBody();
        }
       
    }

    @PatchMapping("/actualizar")
    public Map<String, Object> actualizarPrestamo(@RequestBody EditarStatus status) {
        // La ruta permanece protegida por JWT en SecurityConfig.
        try {
            return service.updatePrestamoUser(status);
        } catch (Exception e) {
            // Manejo de errores y mapeo a una respuesta HTTP adecuada
            return ErrorHttp.mapearError(e).getBody();
        }
    }

    /*private ResponseEntity<Map<String, Object>> mapearError(Exception e) {
        Map<String, Object> errorBody = new java.util.HashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500 por defecto
        String mensaje = e.getMessage();

        // Identificar el tipo de excepción HTTP (Sintaxis moderna Java 14+)
        if (e instanceof org.springframework.web.server.ResponseStatusException httpEx) {
            status = HttpStatus.valueOf(httpEx.getStatusCode().value());
            mensaje = httpEx.getReason();
        } else if (e instanceof org.springframework.security.access.AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            mensaje = "No tienes permisos para realizar esta acción.";
        }

        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());
        errorBody.put("message", mensaje);

        return ResponseEntity.status(status).body(errorBody);
    }*/

}
