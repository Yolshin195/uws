package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.PerformTransactionArguments;
import com.provider.uws.PerformTransactionResult;
import com.provider.uws.handler.AuthenticationHandler;
import com.provider.uws.handler.RequestHandler;
import com.provider.uws.model.*;
import com.provider.uws.model.mapper.TransactionMapper;
import com.provider.uws.service.bd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<GenericParam> paramList = arguments.getParameters();
        Optional<GenericParam> phoneOptional = getPhone(paramList);
        Optional<GenericParam> pinOptional = getPin(paramList);
        if (phoneOptional.isPresent()) {
            Optional<Customer> customerOptional = customerService.findByPhone(
                    phoneOptional.get().getParamValue()
            );
            if (customerOptional.isEmpty()) {
                result.setErrorMsg("Customer not found");
                result.setStatus(400);
                return result;
            }
            customer = customerOptional.get();
        }

        if (customer != null ) {
            Optional<Wallet> walletOptional = walletService.findByProviderAndCustomer(provider, customer);
            if (walletOptional.isEmpty()) {
                result.setErrorMsg("Wallet not found");
                result.setStatus(400);
                return result;
            }
            wallet = walletOptional.get();
        }

        if (!(pinOptional.isPresent() && wallet.getPin().equals(pinOptional.get().getParamValue()))) {
            result.setErrorMsg("The pin is incorrect");
            result.setStatus(401);
            return result;
        }

        Transaction transaction = Transaction.builder()
                .wallet(wallet)
                .transactionId(arguments.getTransactionId())
                .amount(arguments.getAmount())
                .balanceBeforeSurgery(wallet.getBalance())
                .balanceAfterSurgery(wallet.getBalance() + arguments.getAmount())
                .transactionType(TransactionTypeEnum.CREDIT.getValue())
                .transactionTime(fromXMLGregorianCalendar(arguments.getTransactionTime()))
                .timeStamp(LocalDateTime.now())
                .build();

        transaction = transactionEntityService.save(transaction);

        wallet.setBalance(transaction.getBalanceAfterSurgery());

        wallet = walletService.update(wallet);

        GenericParam balance = new GenericParam();
        balance.setParamKey("balance");
        balance.setParamValue(wallet.getBalance().toString());
        result.getParameters().add(balance);

        result.setStatus(200);
        result.setTimeStamp(getTimeStamp());
        result.setProviderTrnId(transaction.getTransactionId());

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
}
