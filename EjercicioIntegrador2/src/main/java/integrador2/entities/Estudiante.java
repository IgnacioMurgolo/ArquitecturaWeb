package integrador2.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private int anioNacimiento;

    @Column
    private String genero;

    @Column
    private int dni;

    @Column
    private String ciudadResidencia;

    @Column
    private long lu;

    // Relación uno a muchos con la entidad Inscripcion
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // CascadeType.ALL significa que todas las operaciones de cascada se aplicarán a las entidades relacionadas
    private List<Inscripcion> inscripciones;

    // Constructores
    public Estudiante() {
        this.inscripciones = new ArrayList<Inscripcion>();
    }

    public Estudiante(String nombre, String apellido, int anioNacimiento, String genero, int dni, String ciudadResidencia, long lu) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.anioNacimiento = anioNacimiento;
        this.genero = genero;
        this.dni = dni;
        this.ciudadResidencia = ciudadResidencia;
        this.lu = lu;
        this.inscripciones = new ArrayList<Inscripcion>();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return LocalDate.now().getYear() - getAnioNacimiento();
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getDni() {
        return dni;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setLu(long lu) {
        this.lu = lu;
    }

    public long getLu() {
        return lu;
    }

    public List<Inscripcion> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(Inscripcion inscripcion) {
        if (!inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
            inscripcion.setEstudiante(this); // Mantener la relación bidireccional
        }
    }

    public void removeInscripcion(Inscripcion inscripcion) {
        if (inscripciones.contains(inscripcion)) {
            inscripciones.remove(inscripcion);
            inscripcion.setEstudiante(null); // Mantener la relación bidireccional
        }
    }

    @Override
    public String toString() {
        return "Estudiante: " +
                " nombre: " + nombre +
                ", apellido: " + apellido +
                ", edad: " + getEdad() +
                ", genero: " + genero +
                ", dni: " + dni +
                ", ciudad de residencia: " + ciudadResidencia +
                ", libreta universitaria: " + lu;
    }
}
