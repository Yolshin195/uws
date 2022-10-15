package com.provider.uws.service.bd;

import com.provider.uws.model.Transaction;
import com.provider.uws.repository.TransactionRepository;

public class TransactionServiceImpl extends BaseEntityServiceImpl<Transaction> {

    public TransactionServiceImpl(TransactionRepository repository) {
        super(repository);
    }
}
