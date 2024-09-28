package integrador2.factories;

import integrador2.repositories.JPACarreraImp;
import integrador2.repositories.JPAEstudianteImp;
import integrador2.repositories.JPAInscripcionImp;

public abstract class RepositoryFactory {
    public static final int MYSQL_JDBC = 1;

    public abstract JPACarreraImp getCarreraRepository();
    public abstract JPAEstudianteImp getEstudianteRepository();
    public abstract JPAInscripcionImp getInscripcionRepository();

    public static RepositoryFactory getRepositoryFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC:
                return new JPAMysqlRepositoryFactory();
            default:
                return null;
        }
    }
}
