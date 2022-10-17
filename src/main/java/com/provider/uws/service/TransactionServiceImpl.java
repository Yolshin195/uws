package com.provider.uws.service;

import com.provider.uws.*;
import com.provider.uws.model.*;
import com.provider.uws.model.mapper.TransactionMapper;
import com.provider.uws.service.bd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProviderService providerService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionEntityService transactionEntityService;

    @Autowired
    TransactionMapper transactionMapper;

    @Override
    @Transactional
    public PerformTransactionResult perform(PerformTransactionArguments arguments) {
        PerformTransactionResult result = new PerformTransactionResult();

        try {
            authentication(arguments);
            Wallet wallet;
            Provider provider = extractProvider(arguments.getServiceId());
            List<GenericParam> paramList = arguments.getParameters();
            Optional<GenericParam> phoneOptional = getPhone(paramList);
            if (phoneOptional.isPresent()) {
                Customer customer = extractCustomer(phoneOptional.get().getParamValue());
                wallet = extractWallet(provider, customer);
            } else {
                wallet = extractWallet(provider, paramList);
            }

            checkPin(wallet, paramList);

            Transaction transaction = transactionEntityService.save(
                    Transaction.builder()
                            .wallet(wallet)
                            .transactionId(arguments.getTransactionId())
                            .amount(arguments.getAmount())
                            .balanceBeforeSurgery(wallet.getBalance())
                            .balanceAfterSurgery(wallet.getBalance() + arguments.getAmount())
                            .transactionType(TransactionTypeEnum.CREDIT.getValue())
                            .transactionTime(fromXMLGregorianCalendar(arguments.getTransactionTime()))
                            .timeStamp(LocalDateTime.now())
                            .build()
            );

            wallet.setBalance(transaction.getBalanceAfterSurgery());
            wallet = walletService.update(wallet);

            GenericParam balance = new GenericParam();
            balance.setParamKey("balance");
            balance.setParamValue(wallet.getBalance().toString());
            result.getParameters().add(balance);

            result.setStatus(200);
            result.setTimeStamp(getTimeStamp());
            result.setProviderTrnId(transaction.getTransactionId());

        } catch (ResponseStatusException e) {
            result.setStatus(e.getStatus().value());
            result.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            result.setStatus(500);
            result.setErrorMsg("External error!");
        }

        return result;
    }

    private LocalDateTime fromXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        return LocalDateTime.of(
                xmlGregorianCalendar.getYear(),
                xmlGregorianCalendar.getMonth(),
                xmlGregorianCalendar.getDay(),
                xmlGregorianCalendar.getHour(),
                xmlGregorianCalendar.getMinute(),
                xmlGregorianCalendar.getSecond(),
                xmlGregorianCalendar.getMillisecond()
        );
    }

    private XMLGregorianCalendar getTimeStamp() {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDateTime.now().toString());
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidLuhn(String value) {
        int sum = Character.getNumericValue(value.charAt(value.length() - 1));
        int parity = value.length() % 2;
        for (int i = value.length() - 2; i >= 0; i--) {
            int summand = Character.getNumericValue(value.charAt(i));
            if (i % 2 == parity) {
                int product = summand * 2;
                summand = (product > 9) ? (product - 9) : product;
            }
            sum += summand;
        }
        return (sum % 10) == 0;
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

    private void checkPin(Wallet wallet, List<GenericParam> paramList) {
        Optional<GenericParam> pinOptional = getPin(paramList);

        if (!(pinOptional.isPresent() && wallet.getPin().equals(pinOptional.get().getParamValue()))) {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The pin is incorrect");
        }
    }

    private Provider extractProvider(Long serviceId) {
        Optional<Provider> providerOptional = providerService.findByServiceId(serviceId);
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

    private Wallet extractWallet(Provider provider, List<GenericParam> paramList) {
        Optional<GenericParam> numberOptional = getNumber(paramList);
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
