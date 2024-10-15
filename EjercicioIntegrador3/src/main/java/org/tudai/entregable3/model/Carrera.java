package org.tudai.entregable3.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inscripcion> inscripciones;


    public Carrera(String nombre) {
        this.nombre = nombre;
        this.inscripciones = new ArrayList<>();
    }

    public Carrera() {
    }

    public void addInscripcion(Inscripcion inscripcion) {
        this.inscripciones.add(inscripcion);
        inscripcion.setCarrera(this);
    }
}
