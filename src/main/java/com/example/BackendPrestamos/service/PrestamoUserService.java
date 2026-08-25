package com.example.BackendPrestamos.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.BackendPrestamos.dto.PrestamoDto;
import com.example.BackendPrestamos.entity.PretamoUser;
import com.example.BackendPrestamos.repository.PrestamoUserRepository;
import com.example.BackendPrestamos.dto.EditarStatus;

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
        pretamoUserEntity.setStatus("PENDING");

        // Mapear los campos del DTO a la entidad
        Repository.save(pretamoUserEntity);
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("message", "Prestamo creado exitosamente desde el service");
        return response;
    }

    public Map<String, Object> updatePrestamoUser(EditarStatus status) {
        // Lógica para actualizar un préstamo
        Map<String, Object> response = new java.util.HashMap<>();
        PretamoUser pretamoUserEntity = new PretamoUser();
        if (status.getStatus() == null || status.getStatus().isEmpty()) {
            response.put("error", "El estado no puede estar vacío");
            return response;
        }
        if (!status.getStatus().equals("PENDING") && !status.getStatus().equals("APPROVED") && !status.getStatus().equals("REJECTED")) {
            response.put("error", "Estado inválido. Debe ser PENDING, APPROVED o REJECTED");
            return response;
        }
        pretamoUserEntity.setStatus(status.getStatus());
        Repository.save(pretamoUserEntity);
        // Aquí puedes implementar la lógica de actualización según tus necesidades
        response.put("message", "Prestamo actualizado exitosamente desde el service");
        return response;
    }
}
