package com.provider.uws.repository;

import com.provider.uws.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseEntityRepository<Customer> {
    Optional<Customer> findByPhoneAndDeleteTsIsNull(String phone);
}
