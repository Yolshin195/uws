package com.provider.uws.service.bd;

import com.provider.uws.model.Provider;
import com.provider.uws.repository.BaseEntityRepository;
import com.provider.uws.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProviderServiceImpl extends BaseEntityServiceImpl<Provider> implements ProviderService {

    protected ProviderRepository repository;

    public ProviderServiceImpl(ProviderRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<Provider> findByServiceId(Long serviceId) {
        return repository.findByServiceIdAndDeleteTsIsNull(serviceId);
    }
}
