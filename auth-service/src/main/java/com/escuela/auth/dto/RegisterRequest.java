package com.escuela.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombreUsuario;
    private String clave;
    private String correo;
}