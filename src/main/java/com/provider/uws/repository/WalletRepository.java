package com.provider.uws.repository;

import com.provider.uws.model.Customer;
import com.provider.uws.model.Provider;
import com.provider.uws.model.Wallet;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends BaseEntityRepository<Wallet> {
    Optional<Wallet> findByProviderAndCustomerAndDeleteTsIsNull(Provider provider, Customer customer);
    Optional<Wallet> findByProviderAndNumberAndDeleteTsIsNull(Provider provider, String number);
}
