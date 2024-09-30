package integrador2.repositories;

import integrador2.dtos.ReporteCarreraDTO;
import integrador2.entities.Carrera;
import integrador2.entities.Estudiante;
import integrador2.entities.Inscripcion;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JPACarreraImp implements EntityRepository<Carrera>{
    private final EntityManager em;
    private static JPACarreraImp instance;

    private JPACarreraImp(EntityManager em) {
        this.em = em;
    }

    public static JPACarreraImp getInstance(EntityManager em) {
        if(instance == null)
            instance = new JPACarreraImp(em);
        return instance;
    }

    @Override
    public void insert(Carrera carrera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(carrera);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al insertar carrera. " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Carrera selectById(int id) {
        try {
            return em.createQuery("SELECT c FROM Carrera c WHERE c.id = :id", Carrera.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carrera por id. " + e.getMessage());
            throw e;
        }
    }

    public Carrera selectByName(String name) {
        try {
            return em.createQuery("SELECT c FROM Carrera c WHERE c.nombre = :nombre", Carrera.class)
                    .setParameter("nombre", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontró ninguna carrera con el nombre especificado.");
            return null;
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carrera por nombre. " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Carrera> selectAll() {
        try {
            return em.createQuery("SELECT c FROM Carrera c", Carrera.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carreras. " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean update(Carrera carrera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            // Buscar si la carrera existe
            Carrera carreraExistente = em.find(Carrera.class, carrera.getId());

            if (carreraExistente != null) {
                // Actualizar los campos necesarios
                carreraExistente.setNombre(carrera.getNombre());

                // Actualizar la lista de inscripciones
                // Eliminar las inscripciones antiguas que no están en la nueva lista
                List<Inscripcion> inscripcionesExistentes = carreraExistente.getInscripciones();

                for (Inscripcion inscripcion : inscripcionesExistentes) {
                    if (!carrera.getInscripciones().contains(inscripcion)) {
                        carreraExistente.removeInscripcion(inscripcion);
                    }
                }

                // Agregar nuevas inscripciones que no están en la lista existente
                for (Inscripcion inscripcion : carrera.getInscripciones()) {
                    if (!inscripcionesExistentes.contains(inscripcion)) {
                        carreraExistente.addInscripcion(inscripcion);
                    }
                }

                em.merge(carreraExistente);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                System.out.println("Carrera no encontrada.");
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al actualizar carrera. " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            Carrera carrera = em.find(Carrera.class, id);

            if (carrera != null) {
                em.remove(carrera);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                System.out.println("La carrera con id: " + id + " no existe.");
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al eliminar carrera. " + e.getMessage());
            return false;
        }
    }

    // 3) Generar un reporte de las carreras
    public List<ReporteCarreraDTO> getInscriptosYEgresadosPorAnio() {
        try {
            // Consulta para inscriptos
            String jpqlInscriptos = "SELECT c.nombre AS nombreCarrera, i.anioInscripcion AS anio, COUNT(i) AS cantInscriptos " +
                    "FROM Inscripcion i JOIN i.carrera c " +
                    "GROUP BY c.nombre, i.anioInscripcion " +
                    "ORDER BY c.nombre ASC, i.anioInscripcion ASC";
            Query queryInscriptos = em.createQuery(jpqlInscriptos);
            List<Object[]> inscriptosResults = queryInscriptos.getResultList();

            // Consulta para egresados
            String jpqlEgresados = "SELECT c.nombre AS nombreCarrera, i.anioEgreso AS anio, COUNT(i) AS cantEgresados " +
                    "FROM Inscripcion i JOIN i.carrera c " +
                    "WHERE i.anioEgreso IS NOT NULL " +
                    "GROUP BY c.nombre, i.anioEgreso " +
                    "ORDER BY c.nombre ASC, i.anioEgreso ASC";
            Query queryEgresados = em.createQuery(jpqlEgresados);
            List<Object[]> egresadosResults = queryEgresados.getResultList();

            // Combinar resultados
            Map<String, Map<Integer, ReporteCarreraDTO>> reporteMap = new HashMap<>();

            for (Object[] result : inscriptosResults) {
                String nombreCarrera = (String) result[0];
                int anio = (Integer) result[1];
                long cantInscriptos = (Long) result[2];

                reporteMap
                        .computeIfAbsent(nombreCarrera, k -> new HashMap<>())
                        .put(anio, new ReporteCarreraDTO(nombreCarrera, anio, cantInscriptos, 0));
            }

            for (Object[] result : egresadosResults) {
                String nombreCarrera = (String) result[0];
                int anio = (Integer) result[1];
                long cantEgresados = (Long) result[2];

                reporteMap
                        .computeIfAbsent(nombreCarrera, k -> new HashMap<>())
                        .computeIfAbsent(anio, k -> new ReporteCarreraDTO(nombreCarrera, anio, 0, 0))
                        .setCantEgresados(cantEgresados);
            }

            return reporteMap.values().stream()
                    .flatMap(map -> map.values().stream())
                    .sorted(Comparator.comparing(ReporteCarreraDTO::getNombre)
                            .thenComparing(ReporteCarreraDTO::getAnio))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /*
    public List<ReporteCarreraDTO> getInscriptosYEgresadosPorAnio() {
        try {
            String sql = "SELECT c.nombre AS nombreCarrera, " +
                   "i.anioInscripcion AS anio, " +
                    "COUNT(i) AS cantInscriptos, " +
                    "SUM(CASE WHEN i.anioEgreso IS NOT NULL THEN 1 ELSE 0 END) AS cantEgresados " +
                    "FROM Inscripcion i " +
                    "JOIN i.carrera c " +
                    "GROUP BY c.nombre, (i.anioInscripcion) " +
                    "ORDER BY c.nombre ASC, (i.anioInscripcion) ASC";

            Query query = em.createQuery(sql);

            List<Object[]> results = query.getResultList();

            return results.stream()
                    .map(result -> new ReporteCarreraDTO(
                            (String) result[0],
                            (Integer) result[1], // Año
                            (Long) result[2],  // Cantidad de inscriptos
                            (Long) result[3]   // Cantidad de egresados
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /*
    public List<ReporteCarreraDTO> generarReporteCarreras() {
        try {
            String jpql = "SELECT new integrador2.dtos.ReporteCarreraDTO(c.nombre, " +
                    "i.anioInscripcion, " +
                    "(SELECT COUNT(i1) FROM Inscripcion i1 WHERE i1.anioInscripcion = i.anioInscripcion AND i1.carrera = c), " + // Cantidad de inscriptos por año
                    "(SELECT COUNT(i2) FROM Inscripcion i2 WHERE i2.anioEgreso = i.anioInscripcion AND i2.carrera = c) ) " + // Cantidad de egresados por año
                    "FROM Inscripcion i " +
                    "JOIN i.carrera c " +
                    "GROUP BY c.nombre, i.anioInscripcion " +
                    "ORDER BY c.nombre ASC, i.anioInscripcion ASC";

            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        }
    }
*/
}
