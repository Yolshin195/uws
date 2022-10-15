package com.provider.uws.ws;

import com.provider.uws.*;
import org.springframework.stereotype.Service;

@Service
public class ProviderWebServiceEndpoint implements ProviderWebService {
    @Override
    public GetStatementResult getStatement(GetStatementArguments arguments) {
        return null;
    }

    @Override
    public GetInformationResult getInformation(GetInformationArguments arguments) {
        return null;
    }

    @Override
    public PerformTransactionResult performTransaction(PerformTransactionArguments arguments) {
        return null;
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
