package org.tudai.tripservice.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.tripservice.dto.PauseDTO;
import org.tudai.tripservice.service.PauseService;

import java.util.List;

@RestController("/pauses")
public class PauseController {
    private final PauseService pauseService;

    @Autowired
    public PauseController(PauseService pauseService) {
        this.pauseService = pauseService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTrip(@RequestBody PauseDTO pauseDTO) {
        try {
            pauseService.save(pauseDTO);
            return ResponseEntity.ok(pauseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            List<PauseDTO> pauses = pauseService.getAll();
            if (pauses.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(pauses);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            pauseService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody PauseDTO pauseDTO) {
        try {
            pauseService.updateById(id, pauseDTO);
            return ResponseEntity.ok("Pausa actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar la pausa");
        }
    }
}
