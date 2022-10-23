package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.GetInformationArguments;
import com.provider.uws.GetInformationResult;
import com.provider.uws.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ExtractFieldService extractField;

    @Autowired
    VerificationService verificationService;

    @Override
    public GetInformationResult getInformation(GetInformationArguments arguments) {
        GetInformationResult result = new GetInformationResult();

        try {
            authenticationService.authentication(arguments);

            Wallet wallet = extractField.extractWallet(arguments.getServiceId(), arguments.getParameters());

            verificationService.checkPin(wallet, arguments.getParameters());

            result.setStatus(200);

            GenericParam balance = new GenericParam();
            balance.setParamKey("balance");
            balance.setParamValue(wallet.getBalance().toString());
            result.getParameters().add(balance);

        } catch (ResponseStatusException e) {
            result.setStatus(e.getStatus().value());
            result.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            result.setStatus(500);
            result.setErrorMsg("External error!");
        }

        return result;
    }

}
