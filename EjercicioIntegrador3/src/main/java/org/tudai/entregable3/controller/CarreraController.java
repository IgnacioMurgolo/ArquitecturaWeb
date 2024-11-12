package org.tudai.entregable3.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.entregable3.dto.CarreraConCantidadInscriptosDTO;
import org.tudai.entregable3.dto.CarreraDTO;
import org.tudai.entregable3.model.Carrera;
import org.tudai.entregable3.service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    private final CarreraService carreraService;

    @Autowired
    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> save(@RequestBody Carrera carrera) {
        try {
            carreraService.save(carrera);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<CarreraDTO>> findAll() {
        try {
            List<CarreraDTO> carreras = carreraService.findAll();
            if (carreras.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(carreras);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            CarreraDTO carrera = carreraService.findById(id);
            return ResponseEntity.ok(carrera);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la carrera con el id " + id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error inesperado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            carreraService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Carrera carrera) {
        try {
            carrera.setId(id);
            carreraService.save(carrera);
            return ResponseEntity.ok("Estudiante actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el estudiante");
        }
    }

    // f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    @GetMapping("/carrerasByCantidadInscriptos")
    public ResponseEntity<List<CarreraConCantidadInscriptosDTO>> getCarrerasByCantidadInscriptos() {
        try {
            List<CarreraConCantidadInscriptosDTO> carreras = carreraService.getCarrerasByCantidadInscriptos();
            if (carreras.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(carreras);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
