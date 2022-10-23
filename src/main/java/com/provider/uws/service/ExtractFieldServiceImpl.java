package com.provider.uws.service;

import com.provider.uws.GenericParam;
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
public class ExtractFieldServiceImpl implements ExtractFieldService {

    @Autowired
    ProviderService providerService;

    @Autowired
    CustomerService customerService;

    @Autowired
    WalletService walletService;

    @Autowired
    VerificationService verificationService;

    @Override
    public Provider extractProvider(Long serviceId) {
        Optional<Provider> providerOptional = providerService.findByServiceId(serviceId);
        if (providerOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service not found");
        }
        return providerOptional.get();
    }

    @Override
    public Customer extractCustomer(String phone) {
        Optional<Customer> customerOptional = customerService
                .findByPhone(phone);
        if (customerOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found");
        }
        return customerOptional.get();
    }

    @Override
    public Wallet extractWallet(Provider provider, Customer customer) {
        Optional<Wallet> walletOptional = walletService.findByProviderAndCustomer(provider, customer);
        if (walletOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet not found");
        }
        return walletOptional.get();
    }

    @Override
    public Wallet extractWallet(Provider provider, List<GenericParam> paramList) {
        Optional<GenericParam> numberOptional = extractNumber(paramList);
        if (numberOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number not found");
        }

        if (!verificationService.isValidLuhn(numberOptional.get().getParamValue())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card number");
        }

        Optional<Wallet> walletOptional = walletService
                .findByProviderAndNumber(provider, numberOptional.get().getParamValue());
        if (walletOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet not found");
        }
        return walletOptional.get();
    }

    @Override
    public Wallet extractWallet(Long serviceId, List<GenericParam> paramList) {
        Provider provider = extractProvider(serviceId);

        Optional<GenericParam> phoneOptional = extractPhone(paramList);
        if (phoneOptional.isPresent()) {
            Customer customer = extractCustomer(phoneOptional.get().getParamValue());
            return extractWallet(provider, customer);
        } else {
            return extractWallet(provider, paramList);
        }
    }

    public Optional<GenericParam> extractNumber(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("number"))
                .findAny();
    }

    public Optional<GenericParam> extractPhone(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("phone"))
                .findAny();
    }

    public Optional<GenericParam> extractPin(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("pin"))
                .findAny();
    }
}
