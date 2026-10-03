package com.escuela.auth.config;

import com.escuela.auth.model.Usuario;
import com.escuela.auth.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                Usuario admin = usuarioRepository.findByNombreUsuario("admin").orElse(null);
                if (admin == null) {
                    admin = Usuario.builder()
                            .nombreUsuario("admin")
                            .clave(passwordEncoder.encode("admin777"))
                            .correo("admin@escuela.com")
                            .build();
                } else {
                    admin.setClave(passwordEncoder.encode("admin777"));
                }
                usuarioRepository.save(admin);
                System.out.println("\n=================================================");
                System.out.println(">>> USUARIO 'admin' CREADO/ACTUALIZADO CON CLAVE 'admin777' <<<");
                System.out.println("=================================================\n");
            } catch (Exception e) {
                System.out.println("Error inicializando datos: " + e.getMessage());
            }
        };
    }
}