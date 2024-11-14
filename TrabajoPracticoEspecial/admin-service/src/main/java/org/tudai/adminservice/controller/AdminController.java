package org.tudai.adminservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.adminservice.dto.AdminDTO;
import org.tudai.adminservice.service.AdminService;

import java.time.LocalDate;
import java.util.List;

@RestController("/admins")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody AdminDTO adminDTO) {
        try {
            adminService.save(adminDTO);
            return ResponseEntity.ok(adminDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            List<AdminDTO> adminDTOS = adminService.getAll();
            if (adminDTOS.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok().body(adminDTOS);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // f. Ajuste de precios para habilitar a partir de cierta fecha
    @PutMapping("/adjust-prices")
    public ResponseEntity<Void> adjustPrices(
            @RequestParam Double newPrice,
            @RequestParam LocalDate effectiveDate) {
        adminService.setFuturePrice(newPrice, effectiveDate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            adminService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        try {
            adminService.update(id, adminDTO);
            return ResponseEntity.ok("Admin actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el admin");
        }
    }
}
