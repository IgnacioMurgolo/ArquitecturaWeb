package integrador2.repositories;

import integrador2.dtos.CarreraCantInscriptosDTO;
import integrador2.entities.Carrera;
import integrador2.entities.Estudiante;
import integrador2.entities.Inscripcion;

import javax.persistence.*;
import java.util.List;

public class JPAInscripcionImp implements EntityRepository<Inscripcion> {
    private EntityManager em;
    private static JPAInscripcionImp instance;

    private JPAInscripcionImp(EntityManager em) {
        this.em = em;
    }

    public static JPAInscripcionImp getInstance(EntityManager em) {
        if (instance == null)
            instance = new JPAInscripcionImp(em);
        return instance;
    }

    @Override
    public void insert(Inscripcion inscripcion) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(inscripcion);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al insertar inscripción. " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Inscripcion selectById(int id) {
        try {
            return em.createQuery("SELECT i FROM Inscripcion i WHERE i.id = :id", Inscripcion.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener inscripcion por id. " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Inscripcion> selectAll() {
        try {
            return em.createQuery("SELECT i FROM Inscripcion i", Inscripcion.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener inscripciones. " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean update(Inscripcion inscripcion) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            // Buscar si la inscripción existe
            Inscripcion inscripcionExistente = em.find(Inscripcion.class, inscripcion.getId());

            if (inscripcionExistente != null) {
                // Actualizar los campos necesarios
                inscripcionExistente.setAnioInscripcion(inscripcion.getAnioInscripcion());
                inscripcionExistente.setAnioEgreso(inscripcion.getAnioEgreso());
                inscripcionExistente.setGraduado(inscripcion.isGraduado());
                inscripcionExistente.setCarrera(inscripcion.getCarrera());
                inscripcionExistente.setEstudiante(inscripcion.getEstudiante());

                // Persistir los cambios
                em.merge(inscripcionExistente);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                System.out.println("Inscripción no encontrada.");
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al actualizar inscripción. " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            // Buscar la inscripción por ID
            Inscripcion inscripcion = em.find(Inscripcion.class, id);

            if (inscripcion != null) {
                em.remove(inscripcion);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                System.out.println("La inscripción con id: " + id + " no existe");
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al eliminar inscripción " + e.getMessage());
            return false;
        }
    }

    public void actualizarAnioEgreso(Estudiante estudiante, Carrera carrera, int anioEgreso) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            // Buscar la inscripción correspondiente
            String jpql = "SELECT i FROM Inscripcion i WHERE i.estudiante = :estudiante AND i.carrera = :carrera";
            TypedQuery<Inscripcion> query = em.createQuery(jpql, Inscripcion.class);
            query.setParameter("estudiante", estudiante);
            query.setParameter("carrera", carrera);

            Inscripcion inscripcion = query.getSingleResult();

            // Actualizar el atributo anioEgreso
            inscripcion.setAnioEgreso(anioEgreso);
            em.merge(inscripcion);

            transaction.commit();
        } catch (NoResultException e) {
            System.out.println("No se encontró ninguna inscripción para el estudiante y carrera especificados.");
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al actualizar el año de egreso. " + e.getMessage());
        } finally {
            em.close();
        }
    }


    // b) Matricular un estudiante en una carrera
    public void matricularEstudiante(Estudiante estudiante, Carrera carrera, int anioInscripcion) {
        Inscripcion inscripcion = new Inscripcion(anioInscripcion, carrera, estudiante);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(inscripcion);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al matricular estudiante. " + e.getMessage());
            throw e;
        }
    }

    // f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    public List<CarreraCantInscriptosDTO> listarCarrerasPorCantidadInscriptos() {
        try {
            String jpql = "SELECT new integrador2.dtos.CarreraCantInscriptosDTO( i.carrera.nombre, COUNT(*)) " +
                    "FROM Inscripcion i " +
                    "GROUP BY i.carrera.nombre " +
                    "HAVING COUNT(i) > 0 " +
                    "ORDER BY COUNT(i) DESC ";
            TypedQuery<CarreraCantInscriptosDTO> query = em.createQuery(jpql, CarreraCantInscriptosDTO.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carreras con inscriptos. " + e.getMessage());
            throw e;
        }
    }
}
