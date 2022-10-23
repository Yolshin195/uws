package com.provider.uws.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = VerificationServiceImpl.class)
public class VerificationServiceImplTests {

    @Autowired
    VerificationService verificationService;

    @Test
    void validLuhnTest() {
        assertTrue(verificationService.isValidLuhn("4242424242424242"));
        assertTrue(verificationService.isValidLuhn("4244731003168315"));
        assertTrue(verificationService.isValidLuhn("9990836456205582"));
    }

    @Test
    void validPhoneTest() {
        assertTrue(verificationService.isValidPhone("+998(93)8729063"));
    }
}
