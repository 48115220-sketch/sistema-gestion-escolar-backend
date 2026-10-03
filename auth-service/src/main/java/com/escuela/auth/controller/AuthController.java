package com.escuela.auth.controller;

import com.escuela.auth.dto.*;
import com.escuela.auth.model.*;
import com.escuela.auth.repository.UsuarioRepository;
import com.escuela.auth.security.JwtService;
import com.escuela.auth.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe");
        }

        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.getNombreUsuario())
                .clave(passwordEncoder.encode(request.getClave()))
                .correo(request.getCorreo())
                .build();

        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con exito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            if (request == null || request.getNombreUsuario() == null || request.getClave() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
            }

            Usuario usuario = usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).orElse(null);

            if (usuario == null || usuario.getClave() == null || !passwordEncoder.matches(request.getClave(), usuario.getClave())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
            }

            String jwtToken = jwtService.generarToken(usuario.getNombreUsuario());
            RefreshToken refreshToken = refreshTokenService.crearRefreshToken(usuario.getNombreUsuario());

            return ResponseEntity.ok(LoginResponse.builder()
                    .token(jwtToken)
                    .refreshToken(refreshToken.getToken())
                    .nombreUsuario(usuario.getNombreUsuario())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en autenticacion: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        if (request == null || request.getRefreshToken() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh Token no valido");
        }

        Optional<RefreshToken> tokenOptional = refreshTokenService.findByToken(request.getRefreshToken());

        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh Token no valido");
        }

        try {
            RefreshToken refreshToken = refreshTokenService.verificarExpiracion(tokenOptional.get());
            Usuario usuario = refreshToken.getUsuario();
            String nuevoToken = jwtService.generarToken(usuario.getNombreUsuario());

            return ResponseEntity.ok(LoginResponse.builder()
                    .token(nuevoToken)
                    .refreshToken(request.getRefreshToken())
                    .nombreUsuario(usuario.getNombreUsuario())
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}