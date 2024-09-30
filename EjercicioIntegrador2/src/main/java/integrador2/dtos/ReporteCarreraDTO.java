package integrador2.dtos;

public class ReporteCarreraDTO {
    private String nombre;
    private Integer anio;
    private long cantInscriptos;
    private long cantEgresados;

    public ReporteCarreraDTO(String nombre, Integer anio, long cantInscriptos, long cantEgresados) {
        this.nombre = nombre;
        this.anio = anio;
        this.cantInscriptos = cantInscriptos;
        this.cantEgresados = cantEgresados;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnio() {
        return anio;
    }

    public long getCantInscriptos() {
        return cantInscriptos;
    }

    public void setCantEgresados(long cantEgresados) {
        this.cantEgresados = cantEgresados;
    }

    public long getCantEgresados() {
        return cantEgresados;
    }

    @Override
    public String toString() {
        return "Reporte de las carreras: " +
                "nombre: " + nombre +
                ", año: " + anio +
                ", cantidad de inscriptos: " + cantInscriptos +
                ", cantidad de egresados: " + cantEgresados;
    }
}
