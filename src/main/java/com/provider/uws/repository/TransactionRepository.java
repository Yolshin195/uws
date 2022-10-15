package com.provider.uws.repository;

import com.provider.uws.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseEntityRepository<Transaction> {
}
