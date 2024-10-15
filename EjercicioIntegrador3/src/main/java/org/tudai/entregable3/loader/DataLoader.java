
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

    public DataLoader(EstudianteService estudianteService, CarreraService carreraService) {
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear algunas carreras
        Carrera ingenieria = new Carrera("ingeniería informatica");
        Carrera medicina = new Carrera("medicina");
        Carrera derecho = new Carrera("derecho");
        Carrera tudai = new Carrera("tudai");


        // Guardar carreras
        carreraService.save(ingenieria);
        carreraService.save(medicina);
        carreraService.save(derecho);
        carreraService.save(tudai);

        // Crear algunos estudiantes
        Estudiante estudiante1 = new Estudiante("Rober", "Perez", "masculino", 123425678L, 1995, "buenos aires", 11223344L);
        Estudiante estudiante2 = new Estudiante("Ana", "Gomez", "femenino", 87654321L, 1998, "rosario", 44332211L);
        Estudiante estudiante3 = new Estudiante("Carlos", "Fernandez", "masculino", 55554444L, 1999, "cordoba", 22334455L);
        Estudiante estudiante4 = new Estudiante("Pedro", "Gonzalez", "masculino", 165132161L, 1987, "tandil", 1651364L);
        Estudiante estudiante5 = new Estudiante("Maria", "Castro", "femenino", 1515319L, 1992, "azul", 254785L);
        Estudiante estudiante6 = new Estudiante("Julieta", "Gomez", "femenino", 365746L, 1979, "ayacucho", 3244869L);
        Estudiante estudiante7 = new Estudiante("Santiago", "McCallister", "masculino", 65874126L, 1988, "buenos aires", 32248578L);
        Estudiante estudiante8 = new Estudiante("Ricardo", "Lopez", "masculino", 7854126L, 1991, "tandil", 24589617L);
        Estudiante estudiante9 = new Estudiante("Sofia", "Di Maria", "femenino", 3214597L, 1992, "cordoba", 2147985L);
        Estudiante estudiante10 = new Estudiante("Juan", "Di Santo", "masculino", 1457863L, 1993, "rosario", 3257194L);

        // Uso del método
        crearInscripcion(2021, null, false, ingenieria, estudiante1);
        crearInscripcion(2020,2024, true, ingenieria, estudiante2);
        crearInscripcion(2023,null, false, ingenieria, estudiante10);
        crearInscripcion(2020, 2023, true, derecho, estudiante1);
        crearInscripcion(2022, 2025, true, derecho, estudiante3);
        crearInscripcion(2018,null, false, derecho, estudiante5);
        crearInscripcion(2019, 2024, true, tudai, estudiante4);
        crearInscripcion(2019, null, false, tudai, estudiante6);
        crearInscripcion(2020,null, false, tudai, estudiante7);
        crearInscripcion(2021,2024, true, tudai, estudiante8);
        crearInscripcion(2022,null, false, tudai, estudiante9);

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

    // Método para crear y asociar inscripciones
    private Inscripcion crearInscripcion(Integer anioInscripcion, Integer anioEgreso, boolean graduado, Carrera carrera, Estudiante estudiante) {
        Inscripcion inscripcion = new Inscripcion(anioInscripcion, anioEgreso, graduado, carrera, estudiante);
        carrera.addInscripcion(inscripcion);
        estudiante.addInscripcion(inscripcion);
        return inscripcion;
    }
}