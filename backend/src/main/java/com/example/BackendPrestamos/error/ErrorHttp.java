package com.example.BackendPrestamos.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class ErrorHttp {

    public static ResponseEntity<Map<String, Object>> mapearError(Exception e) {
        Map<String, Object> errorBody = new HashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String mensaje = e.getMessage();

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
    }
}
