package com.provider.uws.service.bd;

import com.provider.uws.model.Provider;

import java.util.Optional;

public interface ProviderService {
    Optional<Provider> findByServiceId(Long serviceId);
}
