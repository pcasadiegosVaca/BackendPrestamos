package com.example.BackendPrestamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BackendPrestamos.entity.PretamoUser;

@Repository
public interface PrestamoUserRepository extends JpaRepository<PretamoUser, Long> {

}
