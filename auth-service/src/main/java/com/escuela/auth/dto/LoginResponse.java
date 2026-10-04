package com.escuela.auth.dto;

public record LoginResponse(
    String token,
    String refreshToken,
    String nombreUsuario
) {}