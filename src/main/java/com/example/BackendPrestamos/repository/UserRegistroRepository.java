package com.example.BackendPrestamos.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.BackendPrestamos.entity.UserRegistro;

@Repository
public interface UserRegistroRepository extends JpaRepository<UserRegistro, Long> {

}
