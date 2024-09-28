package integrador2.repositories;

import integrador2.dtos.ReporteCarreraDTO;
import integrador2.entities.Carrera;
import integrador2.entities.Estudiante;
import integrador2.entities.Inscripcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

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
    public List<ReporteCarreraDTO> generarReporteCarreras() {
        try {
            String jpql = "SELECT new integrador2.dtos.ReporteCarreraDTO(c.nombre, " +
                    "i.anioInscripcion, " +
                    "i.anioEgreso, " +
                    "(SELECT COUNT(i1) FROM Inscripcion i1 WHERE i1.anioEgreso IS NULL AND i1.carrera = c), " + // Inscripciones sin egreso
                    "(SELECT COUNT(i2) FROM Inscripcion i2 WHERE i2.anioEgreso IS NOT NULL AND i2.carrera = c) ) " + // Inscripciones con egreso
                    "FROM Inscripcion i " +
                    "JOIN i.carrera c " +
                    "JOIN i.estudiante e " +
                    "ORDER BY c.nombre ASC, YEAR(i.anioInscripcion) ASC, YEAR(i.anioEgreso) ASC";

            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        }
    }
}
