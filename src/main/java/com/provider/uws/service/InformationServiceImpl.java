package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.GetInformationArguments;
import com.provider.uws.GetInformationResult;
import com.provider.uws.model.Customer;
import com.provider.uws.model.Provider;
import com.provider.uws.model.Wallet;
import com.provider.uws.service.bd.CustomerService;
import com.provider.uws.service.bd.ProviderService;
import com.provider.uws.service.bd.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CustomerService customerService;

    @Autowired
    WalletService walletService;

    @Autowired
    ProviderService providerService;

    @Override
    public GetInformationResult getInformation(GetInformationArguments arguments) {
        GetInformationResult result = new GetInformationResult();
        List<GenericParam> paramList = arguments.getParameters();
        Customer customer = null;
        Provider provider = null;
        Wallet wallet = null;

        if (!authenticationService.check(arguments.getUsername(), arguments.getPassword())) {
            result.setErrorMsg("The username or password you entered is incorrect");
            result.setStatus(401);
            return result;
        }

        Optional<Provider> providerOptional = providerService.findByServiceId(arguments.getServiceId());
        if (providerOptional.isEmpty()) {
            result.setErrorMsg("Service not found");
            result.setStatus(400);
            return result;
        }
        provider = providerOptional.get();

        Optional<GenericParam> phoneOptional = getPhone(paramList);
        Optional<GenericParam> pinOptional = getPin(paramList);

        if (phoneOptional.isPresent()) {
            Optional<Customer> customerOptional = customerService
                    .findByPhone(phoneOptional.get().getParamValue());

            if (customerOptional.isEmpty()) {
                result.setErrorMsg("Customer not found");
                result.setStatus(400);
                return result;
            }
            customer = customerOptional.get();

            Optional<Wallet> walletOptional = walletService.findByProviderAndCustomer(provider, customer);
            if (walletOptional.isEmpty()) {
                result.setErrorMsg("Wallet not found");
                result.setStatus(400);
                return result;
            }
            wallet = walletOptional.get();

            if (!(pinOptional.isPresent() && wallet.getPin().equals(pinOptional.get().getParamValue()))) {
                result.setErrorMsg("The pin is incorrect");
                result.setStatus(401);
                return result;
            }

            result.setStatus(200);

            GenericParam balance = new GenericParam();
            balance.setParamKey("balance");
            balance.setParamValue(wallet.getBalance().toString());
            result.getParameters().add(balance);

            return result;
        }

        Optional<GenericParam> numberOptional = getNumber(paramList);

        if (numberOptional.isPresent()) {

            Optional<Wallet> walletOptional = walletService
                    .findByProviderAndNumber(provider, numberOptional.get().getParamValue());
            if (walletOptional.isEmpty()) {
                result.setErrorMsg("Wallet not found");
                result.setStatus(400);
                return result;
            }
            wallet = walletOptional.get();

            if (!(pinOptional.isPresent() && wallet.getPin().equals(pinOptional.get().getParamValue()))) {
                result.setErrorMsg("The pin is incorrect");
                result.setStatus(401);
                return result;
            }

            result.setStatus(200);

            GenericParam balance = new GenericParam();
            balance.setParamKey("balance");
            balance.setParamValue(wallet.getBalance().toString());
            result.getParameters().add(balance);
        }

        return result;
    }

    private Optional<GenericParam> getPhone(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("phone"))
                .findAny();
    }

    private Optional<GenericParam> getPin(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("pin"))
                .findAny();
    }

    private Optional<GenericParam> getNumber(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("number"))
                .findAny();
    }
}
