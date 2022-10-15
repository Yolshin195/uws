package com.provider.uws.repository;

import com.provider.uws.model.Provider;

import java.util.Optional;

public interface ProviderRepository extends BaseEntityRepository<Provider> {
    Optional<Provider> findByServiceIdAndDeleteTsIsNull(Long serviceId);
}
