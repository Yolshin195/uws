package com.provider.uws.service.bd;

import com.provider.uws.model.Transaction;
import com.provider.uws.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionEntityServiceImpl extends BaseEntityServiceImpl<Transaction> implements TransactionEntityService {

    TransactionRepository repository;

    public TransactionEntityServiceImpl(TransactionRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Boolean isTransactionNotExist(Long transactionId) {
        return repository.isEmpty(transactionId);
    }
}
