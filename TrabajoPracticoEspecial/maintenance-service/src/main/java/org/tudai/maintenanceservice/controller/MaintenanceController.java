package org.tudai.maintenanceservice.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.maintenanceservice.dto.MaintenanceDTO;
import org.tudai.maintenanceservice.service.MaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService) {this.maintenanceService = maintenanceService;}

    @PostMapping("/register")
    public ResponseEntity<?> registerMaintenance(MaintenanceDTO maintenance) {
        try {
            maintenanceService.save(maintenance);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        try {
            maintenanceService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<MaintenanceDTO>> getAllMaintenances() {
        try {
            List<MaintenanceDTO> result = maintenanceService.findAll();
            if (result.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> getMaintenance(@PathVariable Long id) {
        try {
            MaintenanceDTO result = maintenanceService.findById(id);
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/scooter/{scooterId}")
    public ResponseEntity<List<Long>> getMaintenanceIdsByScooterId(@PathVariable Long scooterId) {
        try {
            List<Long> maintenanceIds = maintenanceService.getMaintenanceIdsByScooterId(scooterId);
            if (maintenanceIds.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(maintenanceIds);

        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/assign-scouter/{maintenanceId}/{scooterId}")
    public ResponseEntity<?> assignScooterToMaintenance(@PathVariable Long maintenanceId, @PathVariable Long scooterId) {
        try {
            maintenanceService.assignScooterToMaintenance(maintenanceId, scooterId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/end-maintenance/{maintenanceId}")
    public ResponseEntity<?> endMaintenance(@PathVariable Long maintenanceId) {
        try {
            maintenanceService.endMaintenance(maintenanceId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
