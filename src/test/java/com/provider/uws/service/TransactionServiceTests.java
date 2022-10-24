package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.PerformTransactionArguments;
import com.provider.uws.PerformTransactionResult;
import com.provider.uws.model.*;
import com.provider.uws.service.bd.TransactionEntityService;
import com.provider.uws.service.bd.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = TransactionServiceImpl.class)
public class TransactionServiceTests {

    @Autowired
    TransactionService transactionService;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    WalletService walletService;

    @MockBean
    TransactionEntityService transactionEntityService;

    @MockBean
    ExtractFieldService extractField;

    private PerformTransactionArguments arguments;

    @BeforeEach
    void init() {
        User user = User.builder()
                .active(true)
                .password("pwd")
                .username("user")
                .build();

        Provider provider = Provider.builder()
                .serviceId(1L)
                .name("UCell")
                .build();

        Customer customer = Customer.builder()
                .phone("+998917813126")
                .name("User1")
                .build();

        Wallet wallet = Wallet.builder()
                .balance(300000L)
                .pin("123456")
                .provider(provider)
                .customer(customer)
                .number("9990836456205582")
                .build();

        this.arguments = new PerformTransactionArguments();
        arguments.setServiceId(1L);
        arguments.setPassword(user.getPassword());
        arguments.setUsername(user.getUsername());
        arguments.setTransactionId(13);
        arguments.setServiceId(1);
        arguments.setAmount(300000);
        arguments.setTransactionTime(getTimeStamp());

        GenericParam pin = new GenericParam();
        pin.setParamKey("pin");
        pin.setParamValue(wallet.getPin());

        GenericParam phone = new GenericParam();
        phone.setParamKey("phone");
        phone.setParamValue(customer.getPhone());

        List<GenericParam> genericParamList = arguments.getParameters();
        genericParamList.add(phone);
        genericParamList.add(pin);

        Mockito.doNothing().when(authenticationService).authentication(arguments);
        Mockito.doNothing().when(authenticationService).checkPin(wallet, genericParamList);
        Mockito.when(transactionEntityService.save(any())).then(returnsFirstArg());
        Mockito.when(walletService.update(wallet)).thenReturn(wallet);
        Mockito.when(extractField.extractWallet(provider.getServiceId(), genericParamList)).thenReturn(wallet);
    }

    @Test
    void performTest() {
        PerformTransactionResult result = transactionService.perform(arguments);

        Assertions.assertEquals("600000", result.getParameters().get(0).getParamValue());
        Assertions.assertEquals(201, result.getStatus());
    }

    private XMLGregorianCalendar getTimeStamp() {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDateTime.now().toString());
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
