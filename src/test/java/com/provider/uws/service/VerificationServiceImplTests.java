package com.provider.uws.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class VerificationServiceImplTests {

    @Autowired
    VerificationService verificationService;

    @Test
    void ValidLuhnTest() {
        Assertions.assertTrue(verificationService.isValidLuhn("4242424242424242"));
        Assertions.assertTrue(verificationService.isValidLuhn("4244731003168315"));
    }
}
