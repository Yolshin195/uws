package com.provider.uws.service.bd;

import com.provider.uws.model.BaseEntity;
import com.provider.uws.repository.BaseEntityRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEntityServiceImpl<T extends BaseEntity> implements BaseEntityService<T> {
    protected BaseEntityRepository<T> repository;

    public BaseEntityServiceImpl(BaseEntityRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        entity.setCreateTs(LocalDateTime.now());
        entity.setUpdateTs(LocalDateTime.now());
        entity.setVersion(1);
        return repository.save(entity);
    }

    @Override
    public T update(T entity) {
        entity.setUpdateTs(LocalDateTime.now());
        return repository.save(entity);
    }

    @Override
    public T delete(T entity) {
        return null;
    }

    @Override
    public T getById(UUID id) {
        return null;
    }
}
