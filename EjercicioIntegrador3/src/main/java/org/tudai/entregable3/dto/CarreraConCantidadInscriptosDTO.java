package org.tudai.entregable3.dto;

import lombok.Getter;

@Getter
public class CarreraConCantidadInscriptosDTO {
    private String nombreCarrera;
    private long cantidadInscriptos;


    public CarreraConCantidadInscriptosDTO(String nombreCarrera, long cantidadInscriptos ) {
        this.nombreCarrera = nombreCarrera;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    @Override
    public String toString() {
        return "Carreras con cantidad de inscriptos: " +
                "nombre de carrera: " + nombreCarrera +
                ", cantidad de inscriptos: " + cantidadInscriptos;
    }
}
