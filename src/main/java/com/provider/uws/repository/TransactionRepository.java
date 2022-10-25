package com.provider.uws.repository;

import com.provider.uws.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseEntityRepository<Transaction> {

    @Query(nativeQuery = true,
        value = "select count(*) <> 0 from uws_transaction where delete_ts is null and transaction_id = :transactionId")
    Boolean isEmpty(Long transactionId);
}
