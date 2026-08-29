package com.example.BackendPrestamos.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackendPrestamos.dto.EditarStatus;
import com.example.BackendPrestamos.dto.PrestamoDto;
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
        pretamoUserEntity.setIdUser(prestamoUser.getIdUser());
        pretamoUserEntity.setMonto(prestamoUser.getMonto());
        pretamoUserEntity.setPlazoDate(prestamoUser.getPlazoDate());
        pretamoUserEntity.setCorreo(prestamoUser.getCorreo());
        pretamoUserEntity.setStatus("PENDING");

        // Mapear los campos del DTO a la entidad.
        Repository.save(pretamoUserEntity);
        Map<String, Object> response = new java.util.HashMap<>();
        Map<String, Object> data = new java.util.HashMap<>();
        response= buscarPrestamoUser(prestamoUser.getIdUser());

        //data.put("monto",Repository.getReferenceById(prestamoUser.getIdUser()));
        //data.put("status",pretamoUserEntity.getStatus())

       
        //data.put("status",pretamoUserEntity.getStatus());
       // response.put("message", "Prestamo creado exitosamente desde el service");
        //response.put("data",data);

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
    
    PretamoUser prestamo = Repository.findById(status.getIdPrestamo())
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró el préstamo para el correo especificado: " + status.getIdPrestamo()));

    // 4. Actualización del estado y guardado
    prestamo.setStatus(status.getStatus());
    Repository.save(prestamo);
    response.put("data",getPrestamoByAll());

    response.put("message", "Prestamo actualizado exitosamente desde el service");
    return response;
}

    public Map<String, Object> buscarPrestamoUser(Long id) {
    Map<String, Object> response = new java.util.HashMap<>();
    List<PretamoUser> listaPrestamos = (List<PretamoUser>) Repository.findByIdUser(id); 


    if (listaPrestamos.isEmpty()) {
            response.put("status", "error");
            response.put("mensaje", "No se encontraron préstamos para el usuario con ID: " + id);
            response.put("data", listaPrestamos); // Retorna la lista vacía []
            return response;
        }
    
        List<Map<String, Object>> listaFiltrada = listaPrestamos.stream().map(prestamo -> {
        Map<String, Object> datosSimplificados =  new java.util.HashMap<>();
        datosSimplificados.put("id", prestamo.getId());
        datosSimplificados.put("idUser", prestamo.getIdUser());
        datosSimplificados.put("monto", prestamo.getMonto());   // Obtiene el Monto (ajusta al nombre de tu getter)
        datosSimplificados.put("status", prestamo.getStatus()); // Obtiene el Status (ajusta al nombre de tu getter)
        return datosSimplificados;
    }).collect(Collectors.toList());

    response.put("status", "success");
    response.put("mensaje", "Préstamos recuperados correctamente.");
    response.put("data", listaFiltrada); // Aquí viaja la lista con todos los préstamos
    //response.put("status", listaPrestamos.get(5)); // Aquí viaja la lista con todos los préstamos

    return response;


    }
    public Map<String, Object> getPrestamoByAll() {
        Map<String, Object> response = new HashMap<>();
        List<PretamoUser> pretamoUsers = Repository.findAll();
        response.put("prestamos", pretamoUsers);
 
        return response;
    }
}
