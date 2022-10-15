package com.provider.uws.repository;

import com.provider.uws.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, UUID> {

}
