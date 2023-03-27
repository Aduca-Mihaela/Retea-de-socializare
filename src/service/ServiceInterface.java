package service;

import domain.Entity;

public interface ServiceInterface<ID, E extends Entity<ID>> {
    E findOne(ID id);
    Iterable<E> findAll();
    E save(E entity);
    E delete(String userName);
    E update(E entity);
}

