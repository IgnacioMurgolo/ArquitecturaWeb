package integrador2.entities;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int anioInscripcion;

    @Column
    private Integer anioEgreso; // usa Integer para permitir valores null

    @Column
    private boolean graduado;

    // Relación muchos a uno con Carrera
    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    // Relación muchos a uno con Estudiante
    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    public Inscripcion() {
        super();
    }

    public Inscripcion(int anioInscripcion, Carrera carrera, Estudiante estudiante) {
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = null;
        this.graduado = false;
        this.carrera = carrera;
        this.estudiante = estudiante;
    }

    public int getId() {
        return id;
    }

    public int getAntiguedad() {
        if (graduado == false)
            return LocalDate.now().getYear() - anioInscripcion;
        else
            return anioEgreso - anioInscripcion;
    }

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public Integer getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(Integer anioEgreso) {
        this.anioEgreso = anioEgreso;
        this.setGraduado(true);
    }

    public boolean isGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return "año de inscripcion= " + anioInscripcion +
                ", año de egreso= " + anioEgreso +
                "antiguedad= " + getAntiguedad() +
                ", es graduado= " + graduado +
                ", en la carrera= " + carrera +
                ", datod del estudiante= " + estudiante;
    }
}
