package org.tudai.scooterservice.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.scooterservice.dto.ScooterDTO;
import org.tudai.scooterservice.entity.Scooter;
import org.tudai.scooterservice.service.ScooterService;

import java.util.List;

@RestController
@RequestMapping("/scooters")
public class ScooterController {
    private final ScooterService scooterService;

    @Autowired
    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerScooter(@RequestBody ScooterDTO scooter) {
        try {
            scooterService.save(scooter);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            List<ScooterDTO> scooterDTOS = scooterService.getAll();
            if (scooterDTOS.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok().body(scooterDTOS);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/getScooterWithDetails/{id}")
    public ResponseEntity<?> getScooterWithDetails(@PathVariable Long id) {
        try {
            ScooterDTO scooterDTO = scooterService.getScooterWithDetails(id);
            return ResponseEntity.ok().body(scooterDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            ScooterDTO scooterDTO = scooterService.findById(id);
            return ResponseEntity.ok(scooterDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con el id " + id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error inesperado");
        }
    }

    @GetMapping("/nearby")
    public List<ScooterDTO> getScootersByLocation(@RequestParam String ubicacion) {
        return scooterService.findNearbyScooters(ubicacion);
    }

    @GetMapping("/operationalScooters")
    public Long countOperationalScooters() {
        return scooterService.countOperationalScooters();
    }

    @GetMapping("/maintenanceScooters")
    public Long countMaintenanceScooters() {
        return scooterService.countMaintenanceScooters();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            scooterService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ScooterDTO scooter) {
        try {
            scooterService.updateById(id, scooter);
            return ResponseEntity.ok("Scooter actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el scooter");
        }
    }
}
