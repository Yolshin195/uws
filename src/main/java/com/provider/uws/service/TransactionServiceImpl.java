package com.provider.uws.service;

import com.provider.uws.*;
import com.provider.uws.model.*;
import com.provider.uws.service.bd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    VerificationService verificationService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionEntityService transactionEntityService;

    @Autowired
    ExtractFieldService extractField;

    @Override
    @Transactional
    public PerformTransactionResult perform(PerformTransactionArguments arguments) {
        PerformTransactionResult result = new PerformTransactionResult();

        try {
            authenticationService.authentication(arguments);

            Wallet wallet = extractField.extractWallet(arguments.getServiceId(), arguments.getParameters());

            authenticationService.checkPin(wallet, arguments.getParameters());

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

            result.setStatus(201);
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
}
