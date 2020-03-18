package ru.itis.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<ID, E> {
    void save(E entity);
    void delete(ID id);
    void update(E entity);
    Optional<E> find(ID id);
    List<E> findAll();
}
