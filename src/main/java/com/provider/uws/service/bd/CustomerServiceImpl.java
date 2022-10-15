package com.provider.uws.service.bd;

import com.provider.uws.model.Customer;
import com.provider.uws.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl extends BaseEntityServiceImpl<Customer> implements CustomerService {
    protected CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<Customer> findByPhone(String phone) {
        return repository.findByPhoneAndDeleteTsIsNull(phone);
    }
}
