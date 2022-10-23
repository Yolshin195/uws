package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.model.Customer;
import com.provider.uws.model.Provider;
import com.provider.uws.model.Wallet;

import java.util.List;
import java.util.Optional;

public interface ExtractFieldService {
    Provider extractProvider(Long serviceId);

    Customer extractCustomer(String phone);

    Wallet extractWallet(Provider provider, Customer customer);

    Wallet extractWallet(Provider provider, List<GenericParam> paramList);

    Wallet extractWallet(Long serviceId, List<GenericParam> paramList);

    Optional<GenericParam> extractNumber(List<GenericParam> paramList);

    Optional<GenericParam> extractPhone(List<GenericParam> paramList);

    Optional<GenericParam> extractPin(List<GenericParam> paramList);
}
