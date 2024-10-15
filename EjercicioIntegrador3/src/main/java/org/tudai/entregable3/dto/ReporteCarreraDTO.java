package org.tudai.entregable3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteCarreraDTO {

    private String nombreCarrera;
    private int anio;
    private Integer cantInscriptos;
    private Integer cantEgresados;

    public ReporteCarreraDTO(String nombreCarrera, int anio, long cantInscriptos, long cantEgresados) {
        this.nombreCarrera = nombreCarrera;
        this.anio = anio;
        this.cantInscriptos = (int) cantInscriptos;
        this.cantEgresados = (int) cantEgresados;
    }

    @Override
    public String toString() {
        return "Reporte de las Carreras:" +
                "nombre de la carrera: " + nombreCarrera +
                ", año:" + anio +
                ", cantidad de inscriptos:" + cantInscriptos +
                ", cantidad de egresados: " + cantEgresados;
    }
}
