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
        return new GetStatementResult();
    }

    @Override
    public GetInformationResult getInformation(GetInformationArguments arguments) {
        try {
            return informationService.getInformation(arguments);
        } catch (Exception e) {
            GetInformationResult result = new GetInformationResult();
            result.setStatus(500);
            result.setErrorMsg("External error!");
            return result;
        }
    }

    @Override
    public PerformTransactionResult performTransaction(PerformTransactionArguments arguments) {
        try {
            return transactionService.perform(arguments);
        } catch (Exception e) {
            PerformTransactionResult result = new PerformTransactionResult();
            result.setStatus(500);
            result.setErrorMsg("External error!");
            return result;
        }
    }

    @Override
    public CheckTransactionResult checkTransaction(CheckTransactionArguments arguments) {
        return new CheckTransactionResult();
    }

    @Override
    public CancelTransactionResult cancelTransaction(CancelTransactionArguments arguments) {
        return new CancelTransactionResult();
    }

    @Override
    public ChangePasswordResult changePassword(ChangePasswordArguments arguments) {
        return new ChangePasswordResult();
    }
}
