package org.tudai.entregable3.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer anioInscripcion;
    @Column
    private Integer anioEgreso;
    @Column
    private boolean graduado;
    @ManyToOne
    @JoinColumn(name = "idCarrera", nullable = false)
    private Carrera carrera;
    @ManyToOne
    @JoinColumn(name = "idEstudiante", nullable = false)
    private Estudiante estudiante;

    public Inscripcion() {}

    public Inscripcion(Integer anioInscripcion, Integer anioEgreso, boolean graduado, Carrera carrera, Estudiante estudiante) {
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.graduado = graduado;
        this.carrera = carrera;
        this.estudiante = estudiante;
    }
}
