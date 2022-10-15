package com.provider.uws.service.bd;

import com.provider.uws.model.Transaction;
import com.provider.uws.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionEntityServiceImpl extends BaseEntityServiceImpl<Transaction> implements TransactionEntityService {

    public TransactionEntityServiceImpl(TransactionRepository repository) {
        super(repository);
    }
}
