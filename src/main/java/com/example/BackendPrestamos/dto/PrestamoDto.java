package com.example.BackendPrestamos.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PrestamoDto {
    
    private long id_user;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate plazoDate;
    private long monto;

}
