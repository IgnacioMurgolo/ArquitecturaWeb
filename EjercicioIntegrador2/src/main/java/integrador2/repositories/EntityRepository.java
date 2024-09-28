package integrador2.repositories;

import java.util.List;

public interface EntityRepository<T> {
    void insert(T t);

    T selectById(int id);

    List<T> selectAll();

    boolean update(T t);

    boolean delete(int id);
}

