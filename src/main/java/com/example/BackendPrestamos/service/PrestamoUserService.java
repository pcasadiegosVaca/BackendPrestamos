package com.example.BackendPrestamos.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.BackendPrestamos.dto.PrestamoDto;
import com.example.BackendPrestamos.dto.EditarStatus;
import com.example.BackendPrestamos.entity.PretamoUser;
import com.example.BackendPrestamos.repository.PrestamoUserRepository;

@Service
public class PrestamoUserService {
    @Autowired
    private final PrestamoUserRepository Repository;

    public PrestamoUserService(PrestamoUserRepository prestamoUserRepository) {
        this.Repository = prestamoUserRepository;
    }

    public Map<String, Object> savePrestamoUser(PrestamoDto prestamoUser) {
        PretamoUser pretamoUserEntity = new PretamoUser();
        pretamoUserEntity.setMonto(prestamoUser.getMonto());
        pretamoUserEntity.setPlazoDate(prestamoUser.getPlazoDate());
        pretamoUserEntity.setId_user(prestamoUser.getId_user());
        pretamoUserEntity.setStatus("PENDING");

        // Mapear los campos del DTO a la entidad.
        Repository.save(pretamoUserEntity);
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("message", "Prestamo creado exitosamente desde el service");
        return response;
    }

    public Map<String, Object> updatePrestamoUser(EditarStatus status) {
    Map<String, Object> response = new java.util.HashMap<>();

    // 1. Validaciones de Estado
    if (status.getStatus() == null || status.getStatus().isEmpty()) {
        response.put("error", "El estado no puede estar vacío");
        return response;
    }
    if (!status.getStatus().equals("PENDING")
            && !status.getStatus().equals("APPROVED")
            && !status.getStatus().equals("REJECTED")) {
        response.put("error", "Estado inválido. Debe ser PENDING, APPROVED o REJECTED");
        return response;
    }

    // 2. Validaciones de Rol
    if (status.getRole() == null || status.getRole().isEmpty()) {
        response.put("error", "El rol no puede estar vacío");
        return response;
    }
    if (status.getRole().equals("USER")) {
        response.put("error", "No se puede actualizar el estado con un rol USER");
        return response;
    }

    // 3. Búsqueda segura evitando duplicados usando 'findFirstByCorreo'
    // Limpiamos espacios con .trim() por si se coló un espacio en blanco en Postman
    String correoFiltro = status.getCorreo_user().trim(); 
    
    PretamoUser prestamo = Repository.findFirstByCorreo(correoFiltro)
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró el préstamo para el correo especificado: " + correoFiltro));

    // 4. Actualización del estado y guardado
    prestamo.setStatus(status.getStatus());
    Repository.save(prestamo);

    response.put("message", "Prestamo actualizado exitosamente desde el service");
    return response;
}


}
