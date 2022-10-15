package com.provider.uws.service.bd;

import com.provider.uws.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByPhone(String phone);
}
