package com.provider.uws.repository;

import com.provider.uws.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, UUID> {

}
