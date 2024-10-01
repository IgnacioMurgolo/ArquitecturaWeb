package integrador2.dtos;

import javax.persistence.Column;

public class EstudianteDTO {
    private String nombre;
    private String apellido;
    private int anioNacimiento;
    private String genero;
    private int dni;
    private String ciudadResidencia;
    private long lu;

    public EstudianteDTO(String nombre, String apellido, int anioNacimiento, String genero, int dni, String ciudadResidencia, long lu) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.anioNacimiento = anioNacimiento;
        this.genero = genero;
        this.dni = dni;
        this.ciudadResidencia = ciudadResidencia;
        this.lu = lu;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public int getDni() {
        return dni;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public long getLu() {
        return lu;
    }

    @Override
    public String toString() {
        return "Estudiante: " +
                "nombre: " + nombre +
                ", apellido: " + apellido +
                ", año de nacimiento: " + anioNacimiento +
                ", genero: " + genero +
                ", dni: " + dni +
                ", ciudad de residencia: " + ciudadResidencia +
                ", libreta universitaria: " + lu;
    }
}
