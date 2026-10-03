package com.escuela.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {

    @JsonProperty("username")
    @JsonAlias({"nombreUsuario", "user"})
    private String nombreUsuario;

    @JsonProperty("password")
    @JsonAlias({"clave", "contrasena"})
    private String clave;
}