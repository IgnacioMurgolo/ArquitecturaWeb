package org.tudai.tripservice.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.tripservice.dto.BenefitsBetweenMonthsDTO;
import org.tudai.tripservice.dto.TripDTO;
import org.tudai.tripservice.service.TripService;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTrip(@RequestBody TripDTO tripDTO) {
        try {
            tripService.save(tripDTO);
            return ResponseEntity.ok(tripDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/account/{accountId}")
    public List<TripDTO> getTripsByAccountId(@PathVariable String accountId) {
        return tripService.findTripsByAccountId(accountId);
    }

    @GetMapping("/tripWithAccount/{id}")
    public TripDTO getTripWithAccount(@PathVariable String id) {
        return tripService.getTripWithAccount(id);
    }

    @GetMapping("/{tripId}/with-pause-time")
    public TripDTO getTripWithPauseTime(@PathVariable String tripId) {
        return tripService.getTripWithPauseTime(tripId);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            List<TripDTO> trips = tripService.getAll();
            if (trips.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(trips);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/countByScooterAndYear")
    public ResponseEntity<Long> countScooterTripByScooterAndYear(@RequestParam Long scooterId, @RequestParam int year) {
        try {
            Long count = tripService.countScooterTripByScooterAndYear(scooterId, year);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/benefitsByYearBetweenMonth")
    public ResponseEntity<List<BenefitsBetweenMonthsDTO>> getBenefitsReport(@RequestParam int year, @RequestParam int startMonth, @RequestParam int endMonth) {
        try {
            List<BenefitsBetweenMonthsDTO> benefits = tripService.getBenefitsReport(year, startMonth, endMonth);
            if (benefits.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(benefits);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            tripService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody TripDTO tripDTO) {
        try {
            tripService.updateById(id, tripDTO);
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el estudiante");
        }
    }

}
