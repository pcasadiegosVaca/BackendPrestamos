package com.example.BackendPrestamos.repository;

//import java.util.Optional;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.BackendPrestamos.entity.PretamoUser;
@Repository
public interface PrestamoUserRepository extends JpaRepository<PretamoUser, Long> {
    Optional<PretamoUser> findFirstByCorreo(String correo);
    List<PretamoUser> findByIdUser(Long idUser);

}
