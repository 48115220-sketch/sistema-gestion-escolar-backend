package com.escuela.admin_service.controller;

import com.escuela.admin_service.entidad.Personal;
import com.escuela.admin_service.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/personal")
public class PersonalController {

    @Autowired
    private PersonalService docenteService;

    @GetMapping
    public List<Personal> listarTodos() {
        return docenteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personal> obtenerPorId(@PathVariable Integer id) {
        Personal docente = docenteService.obtenerPorId(id);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(docente);
    }

    @PostMapping
    public Personal crear(@RequestBody Personal docente) {
        docente.setId(null);
        return docenteService.guardar(docente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personal> actualizar(@PathVariable Integer id, @RequestBody Personal docente) {
        Personal existente = docenteService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        docente.setId(id);
        return ResponseEntity.ok(docenteService.guardar(docente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        docenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}