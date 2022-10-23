package com.provider.uws.service;

import com.provider.uws.PerformTransactionArguments;
import com.provider.uws.PerformTransactionResult;

public interface TransactionService {
    PerformTransactionResult perform(PerformTransactionArguments arguments);
}
