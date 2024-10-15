package org.tudai.entregable3.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.entregable3.dto.EstudianteDTO;
import org.tudai.entregable3.model.Estudiante;
import org.tudai.entregable3.service.EstudianteService;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    //a) dar de alta un estudiante
    @PostMapping("/registrar")
    public ResponseEntity<?> save(@RequestBody Estudiante estudiante) {
        try {
            estudianteService.save(estudiante);
            return ResponseEntity.ok(estudiante);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<EstudianteDTO>> findAll() {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findAll();
            if (estudiantes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            EstudianteDTO estudianteDTO = estudianteService.findById(id);
            return ResponseEntity.ok(estudianteDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el estudiante con el id " + id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error inesperado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            estudianteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        try {
            estudiante.setId(id);
            estudianteService.save(estudiante);
            return ResponseEntity.ok("Estudiante actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el estudiante");
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<EstudianteDTO>> findByNombre(@PathVariable String nombre) {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findByNombre(nombre);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<EstudianteDTO>> findByApellido(@PathVariable String apellido) {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findByApellido(apellido);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple (nombre)
    @GetMapping("/ordenadosPorNombre")
    public ResponseEntity<List<EstudianteDTO>> ordenadosPorNombre() {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findOrderByNombre();
            if (estudiantes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // d) recuperar un estudiante, en base a su número de libreta universitaria
    @GetMapping("/libreta/{lu}")
    public ResponseEntity<EstudianteDTO> getEstudianteByLibretaUniv(@PathVariable long lu) {
        try {
            EstudianteDTO estudianteDTO = estudianteService.findByNumeroLibreta(lu);
            if (estudianteDTO != null) {
                return ResponseEntity.ok(estudianteDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // e) recuperar todos los estudiantes, en base a su género
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<EstudianteDTO>> getEstudiantesPorGenero(@PathVariable String genero) {
        try {
            String gen = genero.toLowerCase();
            List<EstudianteDTO> estudiantes = estudianteService.findByGenero(gen);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
    @GetMapping("/ciudad/{ciudad}/carrera/{carrera}")
    public ResponseEntity<List<EstudianteDTO>> getByCiudadAndCarrera(@PathVariable String ciudad, @PathVariable String carrera) {
        try {
            String c = ciudad.toLowerCase();
            String ca = carrera.toLowerCase();

            List<EstudianteDTO> estudiantes = estudianteService.findByCiudadAndCarrera(c, ca);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
                return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
