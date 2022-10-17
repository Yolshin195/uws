package com.provider.uws.service;

import com.provider.uws.GenericArguments;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        try {
            authentication(arguments);

            Wallet wallet;
            Provider provider = extractProvider(arguments);
            Optional<GenericParam> phoneOptional = getPhone(arguments.getParameters());
            if (phoneOptional.isPresent()) {
                Customer customer = extractCustomer(phoneOptional.get().getParamValue());
                wallet = extractWallet(provider, customer);
            } else {
                wallet = extractWallet(provider, arguments);
            }

            checkPin(arguments, wallet);

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

    private void authentication(GenericArguments arguments) {
        if (!authenticationService.check(arguments.getUsername(), arguments.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The username or password you entered is incorrect");
        }
    }

    private void checkPin(GetInformationArguments arguments, Wallet wallet) {
        Optional<GenericParam> pinOptional = getPin(arguments.getParameters());

        if (!(pinOptional.isPresent() && wallet.getPin().equals(pinOptional.get().getParamValue()))) {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The pin is incorrect");
        }
    }

    private Provider extractProvider(GetInformationArguments arguments) {
        Optional<Provider> providerOptional = providerService.findByServiceId(arguments.getServiceId());
        if (providerOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service not found");
        }
        return providerOptional.get();
    }

    private Customer extractCustomer(String phone) {
        Optional<Customer> customerOptional = customerService
                .findByPhone(phone);
        if (customerOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found");
        }
        return customerOptional.get();
    }

    private Wallet extractWallet(Provider provider, Customer customer) {
        Optional<Wallet> walletOptional = walletService.findByProviderAndCustomer(provider, customer);
        if (walletOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet not found");
        }
        return walletOptional.get();
    }

    private Wallet extractWallet(Provider provider, GetInformationArguments arguments) {
        Optional<GenericParam> numberOptional = getNumber(arguments.getParameters());
        if (numberOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number not found");
        }

        Optional<Wallet> walletOptional = walletService
                .findByProviderAndNumber(provider, numberOptional.get().getParamValue());
        if (walletOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet not found");
        }
        return walletOptional.get();
    }
}
