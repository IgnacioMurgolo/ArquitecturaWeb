package org.tudai.entregable3.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    private CarreraService carreraService;

    @Autowired
    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping("/carreraCantidadInscriptos")
    public ResponseEntity<List<CarreraConCantidadInscriptosDTO>> getCarreraCantidadInscriptos() {
        try {
            List<CarreraConCantidadInscriptosDTO> list = carreraService.getCarreraCantidadInscriptos();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
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

    @GetMapping("/all")
    public ResponseEntity<List<CarreraDTO>> findAll() {
        try{
            List<CarreraDTO> carreras = carreraService.findAll();
            return ResponseEntity.ok(carreras);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraDTO> findById(@PathVariable Long id) {
        try{
            CarreraDTO carrera = carreraService.findById(id);
            return ResponseEntity.ok(carrera);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
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

}
