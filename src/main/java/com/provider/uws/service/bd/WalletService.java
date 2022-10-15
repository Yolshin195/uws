package com.provider.uws.service.bd;

import com.provider.uws.model.Customer;
import com.provider.uws.model.Provider;
import com.provider.uws.model.Wallet;

import java.util.Optional;

public interface WalletService extends BaseEntityService<Wallet> {
    Optional<Wallet> findByProviderAndCustomer(Provider provider, Customer customer);
}
