package org.tudai.entregable3.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.entregable3.dto.EstudianteDTO;
import org.tudai.entregable3.dto.InscripcionDTO;
import org.tudai.entregable3.dto.ReporteCarreraDTO;
import org.tudai.entregable3.model.Carrera;
import org.tudai.entregable3.model.Estudiante;
import org.tudai.entregable3.model.Inscripcion;
import org.tudai.entregable3.service.InscripcionService;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private InscripcionService inscripcionService;

    @Autowired
    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<InscripcionDTO>> findAll() {
        try{
            List<InscripcionDTO> inscripciones = inscripcionService.findAll();
            return ResponseEntity.ok(inscripciones);
        }catch (Exception e){
           return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/reporteCarreras")
    public ResponseEntity<List<ReporteCarreraDTO>> getReporteCarreras() {
        try {
            List<ReporteCarreraDTO> reporte = inscripcionService.getReporteCarreras();
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/graduarEstudiante/{anio}")
    public void actualizarInscripcion(@PathVariable Integer anio, @RequestBody Estudiante estudiante, @RequestBody Carrera carrera) {
        inscripcionService.actualizarInscripcion(anio, true, estudiante.getId(), carrera.getId());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> save(@RequestBody Inscripcion inscripcion) {
        try {
            inscripcionService.save(inscripcion);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            inscripcionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Inscripcion inscripcion) {
        try {
            inscripcion.setId(id);
            inscripcionService.save(inscripcion);
            return ResponseEntity.ok("Inscripcion actualizado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error al actualizar la inscripcion");
        }
    }

}
