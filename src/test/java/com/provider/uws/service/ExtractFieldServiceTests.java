package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.model.Customer;
import com.provider.uws.model.Provider;
import com.provider.uws.model.Wallet;
import com.provider.uws.service.bd.CustomerService;
import com.provider.uws.service.bd.ProviderService;
import com.provider.uws.service.bd.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {ExtractFieldServiceImpl.class, VerificationServiceImpl.class})
public class ExtractFieldServiceTests {
    @Autowired
    ExtractFieldService extractFieldService;

    @MockBean
    ProviderService providerService;

    @MockBean
    CustomerService customerService;

    @MockBean
    WalletService walletService;

    private Provider provider;
    private Customer customer;
    private List<GenericParam> paramList;

    @BeforeEach
    void init() {
        this.provider = Provider.builder()
                .serviceId(1L)
                .name("UCell")
                .build();

        this.customer = Customer.builder()
                .phone("+998(91)7813126")
                .name("User1")
                .build();

        Wallet wallet = Wallet.builder()
                .balance(300000L)
                .pin("123456")
                .provider(provider)
                .customer(customer)
                .number("9990836456205582")
                .build();

        GenericParam number = new GenericParam();
        number.setParamKey("number");
        number.setParamValue(wallet.getNumber());

        GenericParam pin = new GenericParam();
        pin.setParamKey("pin");
        pin.setParamValue(wallet.getPin());

        GenericParam phone = new GenericParam();
        phone.setParamKey("phone");
        phone.setParamValue(customer.getPhone());

        paramList = new ArrayList<>();
        paramList.add(number);
        paramList.add(phone);
        paramList.add(pin);

        Mockito
                .when(providerService.findByServiceId(provider.getServiceId()))
                .thenReturn(Optional.of(provider));

        Mockito
                .when(customerService.findByPhone("998917813126"))
                .thenReturn(Optional.of(customer));

        Mockito
                .when(walletService.findByProviderAndCustomer(provider, customer))
                .thenReturn(Optional.of(wallet));

        Mockito
                .when(walletService.findByProviderAndNumber(provider, wallet.getNumber()))
                .thenReturn(Optional.of(wallet));
    }

    @Test
    void extractProviderTest() {
        assertDoesNotThrow(() -> extractFieldService.extractProvider(provider.getServiceId()));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                extractFieldService.extractProvider(2L));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
    }

    @Test
    void extractCustomerTest() {
        assertDoesNotThrow(() -> extractFieldService.extractCustomer(customer.getPhone()));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                extractFieldService.extractCustomer("fail"));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());

    }

    @Test
    void extractWalletByPhoneTest() {
        assertDoesNotThrow(() -> extractFieldService.extractWallet(provider, customer));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                extractFieldService.extractWallet(null, customer));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
    }

    @Test
    void extractWalletByNumberTest() {
        assertDoesNotThrow(() -> extractFieldService.extractWallet(provider, paramList));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                extractFieldService.extractWallet(provider, new ArrayList<>()));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
    }

    @Test
    void extractWalletByNumberIsEmptyTest() {
        GenericParam empty = new GenericParam();
        empty.setParamKey("number");
        empty.setParamValue("");
        List<GenericParam> emptyNumberList = List.of(empty);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                extractFieldService.extractWallet(provider, emptyNumberList));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
        assertEquals("Wallet number is empty", responseStatusException.getReason());
    }

    @Test
    void extractWalletByNumberIsNotNumberTest() {
        GenericParam empty = new GenericParam();
        empty.setParamKey("number");
        empty.setParamValue("null");
        List<GenericParam> emptyNumberList = List.of(empty);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                extractFieldService.extractWallet(provider, emptyNumberList));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
        assertEquals("Wallet number is not a number", responseStatusException.getReason());
    }

    @Test
    void extractWalletTest() {
        assertDoesNotThrow(() -> extractFieldService.extractWallet(provider.getServiceId(), paramList));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                extractFieldService.extractWallet(3L, paramList));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
    }

    @Test
    void extractNumberTest() {
        assertTrue(extractFieldService.extractNumber(paramList).isPresent());
    }

    @Test
    void extractPhoneTest() {
        assertTrue(extractFieldService.extractPhone(paramList).isPresent());
    }

    @Test
    void extractPinTest() {
        assertTrue(extractFieldService.extractPin(paramList).isPresent());
    }

}
