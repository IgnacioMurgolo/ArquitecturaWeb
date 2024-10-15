package org.tudai.entregable3.dto;

import lombok.Getter;
import org.tudai.entregable3.model.Carrera;
import org.tudai.entregable3.model.Estudiante;

@Getter
public class InscripcionDTO {
    private Integer anioInscripcion;
    private Integer anioEgreso;
    private boolean graduado;
    private String carrera;
    private String estudiante;

    public InscripcionDTO(Integer anioInscripcion, Integer anioEgreso, boolean graduado, String carrera, String estudiante) {
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.graduado = graduado;
        this.carrera = carrera;
        this.estudiante = estudiante;
    }
}
