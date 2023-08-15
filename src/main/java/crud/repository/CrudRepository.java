package crud.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {

    Optional<T> findById(K id);

    Optional<T> findByObject(T t);

    void save(T t);

    void delete(K id);

    List<T> findAll();

    //TODO: void saveAll(List<T>);
}
