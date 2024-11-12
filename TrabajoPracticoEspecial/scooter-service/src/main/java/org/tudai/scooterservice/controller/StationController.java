package org.tudai.scooterservice.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.scooterservice.dto.StationDTO;
import org.tudai.scooterservice.service.StationService;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {
    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStation(@RequestBody StationDTO stationDTO) {
        try {
            stationService.save(stationDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{stationId}/addScooter/{scooterId}") // AGREGAR SCOOTER A LA ESTACION
    public ResponseEntity<?> addScooterToStation(@PathVariable Long stationId, @PathVariable Long scooterId) {
        try {
            stationService.addScooterToStation(stationId, scooterId);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            List<StationDTO> stationDTOS = stationService.getAll();
            if (stationDTOS.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok().body(stationDTOS);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            StationDTO estationDTO = stationService.findById(id);
            return ResponseEntity.ok(estationDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el station con el id " + id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error inesperado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            stationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody StationDTO stationDTO) {
        try{
            stationService.updateById(id, stationDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el station con el id " + id);
        }
    }

}
