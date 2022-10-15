package com.provider.uws.ws;

import com.provider.uws.*;
import com.provider.uws.service.InformationService;
import com.provider.uws.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderWebServiceEndpoint implements ProviderWebService {

    @Autowired
    TransactionService transactionService;

    @Autowired
    InformationService informationService;

    @Override
    public GetStatementResult getStatement(GetStatementArguments arguments) {
        return null;
    }

    @Override
    public GetInformationResult getInformation(GetInformationArguments arguments) {
        informationService.getInformation();
        return null;
    }

    @Override
    public PerformTransactionResult performTransaction(PerformTransactionArguments arguments) {
        return transactionService.perform(arguments);
    }

    @Override
    public CheckTransactionResult checkTransaction(CheckTransactionArguments arguments) {
        return null;
    }

    @Override
    public CancelTransactionResult cancelTransaction(CancelTransactionArguments arguments) {
        return null;
    }

    @Override
    public ChangePasswordResult changePassword(ChangePasswordArguments arguments) {
        return null;
    }
}
