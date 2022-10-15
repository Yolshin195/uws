package com.provider.uws.service.bd;

import com.provider.uws.model.Customer;
import com.provider.uws.model.Provider;
import com.provider.uws.model.Wallet;
import com.provider.uws.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletServiceImpl extends BaseEntityServiceImpl<Wallet> implements WalletService {

    WalletRepository repository;

    public WalletServiceImpl(WalletRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<Wallet> findByProviderAndCustomer(Provider provider, Customer customer) {
        return repository.findByProviderAndCustomerAndDeleteTsIsNull(provider, customer);
    }
}
