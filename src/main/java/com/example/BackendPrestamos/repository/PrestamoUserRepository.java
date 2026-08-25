package com.example.BackendPrestamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BackendPrestamos.entity.PretamoUser;
import java.util.Optional;

@Repository
public interface PrestamoUserRepository extends JpaRepository<PretamoUser, Long> {
    Optional<PretamoUser> findFirstByCorreo(String correo);
    
}
