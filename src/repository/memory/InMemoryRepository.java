package repository.memory;

import domain.Entity;
import repository.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private domain.Validator.Validator<E> validator;
    Map<ID, E> entities;


    public InMemoryRepository(domain.Validator.Validator<E> validator) {
        this.validator = validator;
        this.entities = new HashMap();
    }

    @Override
    public Optional<E> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll() {
        Set<E> allEntities = entities.
                entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public Optional<E> save(E entity)  {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional.ofNullable(entities.remove(id));
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> add(E entity){
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));

    }

    @Override
    public Optional<E> update(E entity)  {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }

}
