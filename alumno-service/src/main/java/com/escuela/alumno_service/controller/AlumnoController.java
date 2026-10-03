package com.escuela.alumno_service.controller;

import com.escuela.alumno_service.entidad.Alumno;
import com.escuela.alumno_service.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @GetMapping
    public List<Alumno> listarTodos() {
        return alumnoRepository.findAll();
    }

    @PostMapping
    public Alumno crear(@RequestBody Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(@PathVariable Integer id, @RequestBody Alumno detalles) {
        return alumnoRepository.findById(id)
            .map(alumno -> {
                alumno.setNombre(detalles.getNombre());
                alumno.setApellido(detalles.getApellido());
                alumno.setDni(detalles.getDni());
                alumno.setCursoId(detalles.getCursoId());
                Alumno actualizado = alumnoRepository.save(alumno);
                return ResponseEntity.ok(actualizado);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}