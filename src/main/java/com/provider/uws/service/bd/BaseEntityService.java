package com.provider.uws.service.bd;

import com.provider.uws.model.BaseEntity;

import java.util.UUID;

public interface BaseEntityService<T extends BaseEntity> {
    T save(T entity);
    T update(T entity);
    T delete(T entity);
    T getById(UUID id);
}
