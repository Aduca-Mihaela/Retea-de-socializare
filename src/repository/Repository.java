package repository;

import domain.Entity;

import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {
    Optional<E> findOne(ID var1);

    Iterable<E> findAll();

    Optional<E> save(E var1);

    Optional<E> delete(ID var1);

    Optional<E> add(E entity);

    Optional<E> update(E var1);
}

