package integrador2.dtos;

public class ReporteCarreraDTO {
    private String nombre;
    private Integer anioInscripcion;
    private Integer anioEgreso;
    private Long cantInscriptos;
    private Long cantEgresados;

    public ReporteCarreraDTO(String nombre, Integer anioInscripcion, Integer anioEgreso, Long cantInscriptos, Long cantEgresados) {
        this.nombre = nombre;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.cantInscriptos = cantInscriptos;
        this.cantEgresados = cantEgresados;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnioInscripcion() {
        return anioInscripcion;
    }

    public Integer getAnioEgreso() {
        return anioEgreso;
    }

    public Long getCantInscriptos() {
        return cantInscriptos;
    }

    public Long getCantEgresados() {
        return cantEgresados;
    }

    @Override
    public String toString() {
        return "Reporte de las carreras: " +
                "nombre: " + nombre +
                ", año de inscripcion: " + anioInscripcion +
                ", año de egreso: " + anioEgreso +
                ", cantidad de inscriptos: " + cantInscriptos +
                ", cantidad de egresados: " + cantEgresados;
    }
}
