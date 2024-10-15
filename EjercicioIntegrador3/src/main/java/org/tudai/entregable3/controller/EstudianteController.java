package org.tudai.entregable3.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
            if (estudiantes != null) {
                return ResponseEntity.ok(estudiantes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> findById(@PathVariable Long id) {
        try {
            EstudianteDTO estudianteDTO = estudianteService.findById(id);
            return ResponseEntity.ok(estudianteDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
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
    public ResponseEntity<EstudianteDTO> findByNombre(@PathVariable String nombre) {
        try {
            EstudianteDTO estudianteDTO = estudianteService.findByNombre(nombre);
            if (estudianteDTO != null) {
                return ResponseEntity.ok(estudianteDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<EstudianteDTO> findByApellido(@PathVariable String apellido) {
        try {
            EstudianteDTO estudianteDTO = estudianteService.findByApellido(apellido);
            if (estudianteDTO != null) {
                return ResponseEntity.ok(estudianteDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/estudiantesOrdenadosPorNombre")
    public ResponseEntity<List<EstudianteDTO>> estudiantesOrdenadosPorNombre() {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findEstudiantesOrdByNombre();
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/libreta/{lu}")
    public ResponseEntity<EstudianteDTO> estudiantesLibretaUniv(@PathVariable long lu) {
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

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<EstudianteDTO>> estudiantesGenero(@PathVariable String genero) {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findByGenero(genero);
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/ciudad/{ciudad}/carrera/{carrera}")
    public ResponseEntity<List<EstudianteDTO>> getEstudiantesPorCarreraCiudad(@PathVariable String ciudad, @PathVariable String carrera) {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findByCiudadCarrera(ciudad, carrera);
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
