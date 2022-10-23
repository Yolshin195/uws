package com.provider.uws.service.bd;

import com.provider.uws.model.BaseEntity;

import java.util.Optional;
import java.util.UUID;

public interface BaseEntityService<T extends BaseEntity> {
    T save(T entity);
    T update(T entity);
    T delete(T entity);
    Optional<T> findById(UUID id);
}
