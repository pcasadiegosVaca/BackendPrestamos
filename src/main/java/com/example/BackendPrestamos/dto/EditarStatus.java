package com.example.BackendPrestamos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarStatus {
    private String correo_user;
    private String role;
    private String status;
}
