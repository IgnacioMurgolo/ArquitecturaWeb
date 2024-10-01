package integrador2.main;

import integrador2.dtos.CarreraCantInscriptosDTO;
import integrador2.dtos.EstudianteDTO;
import integrador2.factories.JPAMysqlRepositoryFactory;
import integrador2.factories.RepositoryFactory;
import integrador2.repositories.JPACarreraImp;
import integrador2.repositories.JPAEstudianteImp;
import integrador2.repositories.JPAInscripcionImp;
import integrador2.dtos.ReporteCarreraDTO;
import integrador2.entities.Carrera;
import integrador2.entities.Estudiante;
import integrador2.entities.Inscripcion;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Estudiante e = new Estudiante("Santiago", "Richards", 1992, "masculino", 36254123, "Tandil", 12456);
        Estudiante e1 = new Estudiante("Ignacio", "Perez", 2004, "masculino", 42214129, "Juarez", 12457);
        Estudiante e2 = new Estudiante("Alejo", "Martinez", 1993, "masculino", 37845698, "Capital Federal", 12458);
        Estudiante e3 = new Estudiante("Claudia", "Rodriguez", 1991, "femenino", 35456123, "Tandil", 12459);
        Estudiante e4 = new Estudiante("Angel", "Gomez", 1995, "masculino", 38456912, "Rauch", 12460);
        Estudiante e5 = new Estudiante("Martina", "Soto", 2001, "femenino", 39456123, "Ayacucho", 12461);
        Estudiante e6 = new Estudiante("Almendra", "Risso", 1997, "femenino", 38423123, "Olavarria", 12462);
        Estudiante e7 = new Estudiante("Juan", "Bouysede", 1990, "masculino", 34456923, "Ayacucho", 12463);
        Estudiante e8 = new Estudiante("Agustina", "Mendez", 2001, "femenino", 39486173, "Olavarria", 12464);
        Estudiante e9 = new Estudiante("Melina", "Risotto", 1998, "femenino", 39452138, "Tandil", 12465);

        Carrera c1 = new Carrera("TUDAI");
        Carrera c2 = new Carrera("Licenciatura en Fisica");
        Carrera c3 = new Carrera("Licenciatura en Astronomia");

        Inscripcion i = new Inscripcion(2011, c1, e);
        Inscripcion i1 = new Inscripcion(2018, c2, e);
        Inscripcion i2 = new Inscripcion(2020, c3, e1);
        Inscripcion i3 = new Inscripcion(2016, c2, e2);
        Inscripcion i4 = new Inscripcion(2022, c3, e1);
        Inscripcion i5 = new Inscripcion(2015, c1, e3);
        Inscripcion i6 = new Inscripcion(2021, c1, e4);
        Inscripcion i7 = new Inscripcion(2012, c3, e5);
        Inscripcion i8 = new Inscripcion(2022, c3, e6);
        Inscripcion i9 = new Inscripcion(2013, c2, e7);
        Inscripcion i10 = new Inscripcion(2011, c1, e8);
        Inscripcion i11 = new Inscripcion(2024, c3, e9);

        // Obtener la instancia Singleton de RepositoryFactory
        RepositoryFactory rf = JPAMysqlRepositoryFactory.getRepositoryFactory(1);

        assert rf != null;
        // Obtener los diferentes repositorios utilizando la misma instancia de RepositoryFactory
        JPAEstudianteImp estudianteImp = rf.getEstudianteRepository();
        JPAInscripcionImp inscripcionImp = rf.getInscripcionRepository();
        JPACarreraImp carreraImp = rf.getCarreraRepository();

        /*
        //a) dar de alta un estudiante
        estudianteImp.insert(e);
        estudianteImp.insert(e1);
        estudianteImp.insert(e2);
        estudianteImp.insert(e3);
        estudianteImp.insert(e4);
        estudianteImp.insert(e5);
        estudianteImp.insert(e6);
        estudianteImp.insert(e7);
        estudianteImp.insert(e8);
        estudianteImp.insert(e9);

        carreraImp.insert(c1);
        carreraImp.insert(c2);
        carreraImp.insert(c3);

        //b) matricular un estudiante en una carrera
        inscripcionImp.insert(i);
        inscripcionImp.insert(i1);
        inscripcionImp.insert(i2);
        inscripcionImp.insert(i3);
        inscripcionImp.insert(i4);
        inscripcionImp.insert(i5);
        inscripcionImp.insert(i6);
        inscripcionImp.insert(i7);
        inscripcionImp.insert(i8);
        inscripcionImp.insert(i9);
        inscripcionImp.insert(i10);
        inscripcionImp.insert(i11);
*/
        //c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple
        System.out.println("c) Listado de estudiantes ordenados por nombre");
        List<EstudianteDTO> estudiantesOrdenados = estudianteImp.obtenerEstudiantesOrdenadosPorNombre();
        for (EstudianteDTO estudiante : estudiantesOrdenados) {
            System.out.println(estudiante.toString());
        }
        System.out.println("------------------------------------------------------");
        //d) recuperar un estudiante, en base a su número de libreta universitaria
        System.out.println("d) Recuperar un estudiante por su numero de libreta: 12456");
        long libreta = 12456;
        EstudianteDTO recuperado = estudianteImp.obtenerEstudiantePorLu(libreta);
        System.out.println(recuperado.toString());
        System.out.println("------------------------------------------------------");
        // e) recuperar todos los estudiantes, en base a su género
        System.out.println("e) Buscar por genero: femenino");
        List<EstudianteDTO> mujeres = estudianteImp.obtenerEstudiantesPorGenero("femenino");
        for (EstudianteDTO estudiante : mujeres) {
            System.out.println(estudiante.toString());
        }
        System.out.println("e) Buscar por genero: masculino");
        List<EstudianteDTO> hombres = estudianteImp.obtenerEstudiantesPorGenero("masculino");
        for (EstudianteDTO estudiante : hombres) {
            System.out.println(estudiante.toString());
        }
        System.out.println("-------------------------------------------------------");
        //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
        System.out.println("f) Listado de carreras ordenada por cantidad de inscriptos");
        List<CarreraCantInscriptosDTO> carreras = inscripcionImp.listarCarrerasPorCantidadInscriptos();
        for (CarreraCantInscriptosDTO reporte : carreras) {
            System.out.println(reporte.toString());
        }
        System.out.println("-------------------------------------------------------");
        //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
        System.out.println("g) Listar los alumnos que estudian Licenciatura en Astronomia y son de Tandil");
        Carrera carrera = carreraImp.selectById(2);
        List<EstudianteDTO> estudiantesBuscados = estudianteImp.recuperarEstudiantesPorCarreraYCiudad(carrera, "Tandil");
        for (EstudianteDTO estudiante : estudiantesBuscados) {
            System.out.println(estudiante.toString());
        }
        System.out.println("--------------------------------------------------------");
/*
        // primero egresar estudiantes
        // Imprimir nombres de estudiante y carrera al cual cambiarle el año de egreso de la inscripcion
        System.out.println("Nombre del estudiante persistido: Ignacio");
        System.out.println("Nombre de la carrera persistida: Licenciatura en Astronomia");
        // Obtener las entidades persistidas por su id
        Estudiante estudiantePersistido = estudianteImp.selectById(6);
        Carrera carreraPersistida = carreraImp.selectById(3);
        //      tambien se puede buscar por nombre
        // Estudiante estudiantePersistido = estudianteImp.selectByName("Martina");
        // Carrera carreraPersistida = carreraImp.selectByName("Licenciatura en Astronomia");
        // chequeo que sean correctos
        System.out.println("El id del estudiante es: " + estudiantePersistido.getId() + " y el nombre: " + estudiantePersistido.getNombre());
        System.out.println("El id de la carrera es: " + carreraPersistida.getId() + " y el nombre: " + carreraPersistida.getNombre());

        if (estudiantePersistido != null && carreraPersistida != null) {
            // Actualizar año de egreso
            Integer anioEgreso = 2016;
            inscripcionImp.actualizarAnioEgreso(estudiantePersistido, carreraPersistida, anioEgreso);
        } else {
            System.out.println("No se pudieron obtener las entidades persistidas.");
        }
        System.out.println("-----------------------------------------------------------------");
*/
        // 3)Generar un reporte de las carreras, que para cada carrera incluya información de los
        //inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
        //los años de manera cronológica.

        System.out.println("Reporte de las carreras por año indicando cantidad de inscriptos y egresos: ");
        List<ReporteCarreraDTO> reporte = carreraImp.getInscriptosYEgresadosPorAnio();
        if (reporte != null) {
            for (ReporteCarreraDTO r : reporte) {
                System.out.println(r.toString());
            }
        } else {
            System.out.println("No hay carreras");
        }

    }
}
