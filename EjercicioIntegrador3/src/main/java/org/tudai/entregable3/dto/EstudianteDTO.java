package org.tudai.entregable3.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class EstudianteDTO {
    private String nombres;
    private String apellido;
    private Integer anioNacimiento;
    private String genero;
    private Long dni;
    private String ciudadResidencia;
    private Long libretaUniv;
    private List<InscripcionDTO> inscripciones;

    public EstudianteDTO(String nombres, String apellido, Integer anioNacimiento, String genero, Long dni, String ciudadResidencia, Long libretaUniv, List<InscripcionDTO> inscripciones) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.anioNacimiento = anioNacimiento;
        this.genero = genero;
        this.dni = dni;
        this.ciudadResidencia = ciudadResidencia;
        this.libretaUniv = libretaUniv;
        this.inscripciones = inscripciones;
    }

    public EstudianteDTO(String nombres, String apellido, Integer anioNacimiento, String genero, Long dni, String ciudadResidencia, Long libretaUniv) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.anioNacimiento = anioNacimiento;
        this.genero = genero;
        this.dni = dni;
        this.ciudadResidencia = ciudadResidencia;
        this.libretaUniv = libretaUniv;
        this.inscripciones = new ArrayList<>();
    }
}
