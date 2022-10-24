package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.GetInformationArguments;
import com.provider.uws.GetInformationResult;
import com.provider.uws.model.Customer;
import com.provider.uws.model.Provider;
import com.provider.uws.model.User;
import com.provider.uws.model.Wallet;
import com.provider.uws.service.bd.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {InformationServiceImpl.class, AuthenticationServiceImpl.class})
public class InformationServiceTests {

    @Autowired
    InformationService informationService;

    @MockBean
    ExtractFieldService extractField;

    @MockBean
    UserService userService;

    private GetInformationArguments arguments;

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

        this.arguments = new GetInformationArguments();
        arguments.setServiceId(1L);
        arguments.setPassword(user.getPassword());
        arguments.setUsername(user.getUsername());

        GenericParam pin = new GenericParam();
        pin.setParamKey("pin");
        pin.setParamValue(wallet.getPin());

        GenericParam phone = new GenericParam();
        phone.setParamKey("phone");
        phone.setParamValue(customer.getPhone());

        List<GenericParam> genericParamList = arguments.getParameters();
        genericParamList.add(phone);
        genericParamList.add(pin);

        Mockito.when(userService.findByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(extractField.extractPin(genericParamList)).thenReturn(Optional.of(pin));
        Mockito.when(extractField.extractWallet(provider.getServiceId(), genericParamList)).thenReturn(wallet);

    }

    @Test
    void getInformationTest() {
        GetInformationResult result = informationService.getInformation(arguments);

        Assertions.assertEquals("300000", result.getParameters().get(0).getParamValue());
        Assertions.assertEquals(200, result.getStatus());
    }
}
