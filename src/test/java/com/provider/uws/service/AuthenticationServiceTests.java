package com.provider.uws.service;

import com.provider.uws.GenericArguments;
import com.provider.uws.GenericParam;
import com.provider.uws.model.User;
import com.provider.uws.model.Wallet;
import com.provider.uws.service.bd.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthenticationServiceImpl.class)
public class AuthenticationServiceTests {

    @MockBean
    UserService userService;

    @MockBean
    ExtractFieldService extractFieldService;

    @Autowired
    AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        User user = User.builder()
                .active(true)
                .password("pwd")
                .username("user")
                .build();
        Mockito.when(userService.findByUsername(user.getUsername())).thenReturn(user);

    }

    @Test
    void checkTest() {
        assertTrue(authenticationService.check("user", "pwd"));
        assertFalse(authenticationService.check("user", "fail"));
        assertFalse(authenticationService.check("user2", "pws"));
    }

    @Test
    void authenticationTest() {
        GenericArguments genericArguments = new GenericArguments();
        genericArguments.setUsername("user");
        genericArguments.setPassword("pwd");

        assertDoesNotThrow(() -> authenticationService.authentication(genericArguments));
    }

    @Test
    void authenticationOnFailTest() {
        GenericArguments genericArguments = new GenericArguments();
        genericArguments.setUsername("user");
        genericArguments.setPassword("fail");

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                authenticationService.authentication(genericArguments));

        assertEquals(HttpStatus.UNAUTHORIZED, responseStatusException.getStatus());

    }

    @Test
    void checkPinTest() {
        Wallet walletMock = Wallet.builder()
                .pin("123456")
                .balance(0L)
                .build();

        GenericParam genericParamPin = new GenericParam();
        genericParamPin.setParamKey("pin");
        genericParamPin.setParamValue(walletMock.getPin());

        List<GenericParam> paramList = new ArrayList<>();
        paramList.add(genericParamPin);

        Mockito.when(extractFieldService.extractPin(paramList)).thenReturn(Optional.of(genericParamPin));

        assertDoesNotThrow(() ->
                authenticationService.checkPin(walletMock, paramList));
    }

    @Test
    void checkPinOnFailTest() {
        Wallet walletMock = Wallet.builder()
                .pin("123456")
                .balance(0L)
                .build();

        GenericParam genericParamPin = new GenericParam();
        genericParamPin.setParamKey("pin");
        genericParamPin.setParamValue("fail");

        List<GenericParam> paramList = new ArrayList<>();
        paramList.add(genericParamPin);

        Mockito.when(extractFieldService.extractPin(paramList)).thenReturn(Optional.of(genericParamPin));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () ->
                authenticationService.checkPin(walletMock, paramList));

        assertEquals(HttpStatus.UNAUTHORIZED, responseStatusException.getStatus());

    }
}
