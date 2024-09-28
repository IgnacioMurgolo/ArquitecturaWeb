package integrador2.factories;

import integrador2.repositories.JPACarreraImp;
import integrador2.repositories.JPAEstudianteImp;
import integrador2.repositories.JPAInscripcionImp;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAMysqlRepositoryFactory extends RepositoryFactory {
    private static final String PERSISTENCE_UNIT_NAME = "integrador2";

    @Override
    public JPACarreraImp getCarreraRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return JPACarreraImp.getInstance(em);
    }

    @Override
    public JPAEstudianteImp getEstudianteRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return JPAEstudianteImp.getInstance(em);
    }

    @Override
    public JPAInscripcionImp getInscripcionRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return JPAInscripcionImp.getInstance(em);
    }
}
