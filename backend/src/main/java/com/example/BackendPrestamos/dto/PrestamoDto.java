package com.example.BackendPrestamos.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PrestamoDto {
    
    private Long idUser;
    private Long monto;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("plazo_date")
    private LocalDate plazoDate;
    private String correo;

}
