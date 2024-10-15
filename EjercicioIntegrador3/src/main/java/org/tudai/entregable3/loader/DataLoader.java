
package org.tudai.entregable3.loader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.tudai.entregable3.model.Carrera;
import org.tudai.entregable3.model.Estudiante;
import org.tudai.entregable3.model.Inscripcion;
import org.tudai.entregable3.service.CarreraService;
import org.tudai.entregable3.service.EstudianteService;
import org.tudai.entregable3.service.InscripcionService;

@Component
public class DataLoader implements CommandLineRunner {

    private final EstudianteService estudianteService;
    private final CarreraService carreraService;
    private final InscripcionService inscripcionService;

    public DataLoader(EstudianteService estudianteService, CarreraService carreraService, InscripcionService inscripcionService) {
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
        this.inscripcionService = inscripcionService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear algunas carreras
        Carrera ingenieria = new Carrera("Ingeniería Informática");
        Carrera medicina = new Carrera("Medicina");
        Carrera derecho = new Carrera("Derecho");
        Carrera tudai = new Carrera("TUDAI");


        // Guardar carreras
        carreraService.save(ingenieria);
        carreraService.save(medicina);
        carreraService.save(derecho);
        carreraService.save(tudai);

        // Crear algunos estudiantes
        Estudiante estudiante1 = new Estudiante("Rober", "Pérez", "Masculino", 123425678L, 1995, "Buenos Aires", 11223344L);
        Estudiante estudiante2 = new Estudiante("Ana", "Gómez", "Femenino", 87654321L, 1998, "Rosario", 44332211L);
        Estudiante estudiante3 = new Estudiante("Carlos", "Fernández", "Masculino", 55554444L, 1999, "Córdoba", 22334455L);
        Estudiante estudiante4 = new Estudiante("Pedro", "Gonzalez", "Masculino", 165132161L, 1987, "Tandil", 1651364L);
        Estudiante estudiante5 = new Estudiante("Maria", "Castro", "Femenino", 1515319L, 1992, "Azul", 254785L);
        Estudiante estudiante6 = new Estudiante("Julieta", "Gomez", "Femenino", 365746L, 1979, "Ayacucho", 3244869L);
        Estudiante estudiante7 = new Estudiante("Santiago", "McCallister", "Masculino", 65874126L, 1988, "Buenos Aires", 32248578L);
        Estudiante estudiante8 = new Estudiante("Ricardo", "Lopez", "Masculino", 7854126L, 1991, "Tandil", 24589617L);
        Estudiante estudiante9 = new Estudiante("Sofia", "Di Maria", "Femenino", 3214597L, 1992, "Cordoba", 2147985L);
        Estudiante estudiante10 = new Estudiante("Juan", "Di Santo", "Masculino", 1457863L, 1993, "Rosario", 3257194L);

        // Crear algunas inscripciones
        Inscripcion inscripcion1 = new Inscripcion(2020, null, false, ingenieria, estudiante1);
        Inscripcion inscripcion2 = new Inscripcion(2021, null, false, medicina, estudiante2);
        Inscripcion inscripcion3 = new Inscripcion(2020, 2023, true, derecho, estudiante1); // Egresado
        Inscripcion inscripcion4 = new Inscripcion(2021, null, false, tudai, estudiante4);
        Inscripcion inscripcion5 = new Inscripcion(2021, null, false, derecho, estudiante1);
        Inscripcion inscripcion6 = new Inscripcion(2021, 2024, false, medicina, estudiante3);
        Inscripcion inscripcion7 = new Inscripcion(2021, 2025, false, tudai, estudiante7);
        Inscripcion inscripcion8 = new Inscripcion(2021, null, false, ingenieria, estudiante6);
        Inscripcion inscripcion9 = new Inscripcion(2022, null, false, medicina, estudiante5);

        /*
        // Añadir inscripciones a estudiantes
        estudiante1.addInscripcion(inscripcion1);
        estudiante1.addInscripcion(inscripcion3);
        estudiante2.addInscripcion(inscripcion2);
        estudiante3.addInscripcion(inscripcion6);
        estudiante4.addInscripcion(inscripcion4);
        estudiante5.addInscripcion(inscripcion9);
        estudiante6.addInscripcion(inscripcion8);
        estudiante7.addInscripcion(inscripcion7);

        */
        // Guardar estudiantes (esto también guardará las inscripciones debido al CascadeType.ALL)
        estudianteService.save(estudiante1);
        estudianteService.save(estudiante2);
        estudianteService.save(estudiante3);
        estudianteService.save(estudiante4);
        estudianteService.save(estudiante5);
        estudianteService.save(estudiante6);
        estudianteService.save(estudiante7);
        estudianteService.save(estudiante8);
        estudianteService.save(estudiante9);
        estudianteService.save(estudiante10);


        System.out.println("Datos iniciales cargados correctamente.");

    }

}