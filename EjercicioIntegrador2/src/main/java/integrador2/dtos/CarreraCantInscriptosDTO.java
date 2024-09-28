package integrador2.dtos;

import java.io.Serializable;

public class CarreraCantInscriptosDTO implements Serializable {
    private String nombreCarrera;
    private long cantidadInscriptos;

    public CarreraCantInscriptosDTO() {
    }

    public CarreraCantInscriptosDTO(String nombreCarrera, long cantidadInscriptos) {
        this.nombreCarrera = nombreCarrera;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(long cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
    }

    @Override
    public String toString() {
        return "Reporte de Carrera: " +
                "nombre de la carrera= " + nombreCarrera +
                ", cantidad de inscriptos= " + cantidadInscriptos;
    }
}
