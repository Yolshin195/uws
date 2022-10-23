package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    ExtractFieldService extractField;

    public boolean isValidPhone(String phoneNumber) {
        return true;
    }

    public boolean isValidLuhn(String value) {
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

    public void checkPin(Wallet wallet, List<GenericParam> paramList) {
        Optional<GenericParam> pinOptional = extractField.extractPin(paramList);

        if (!(pinOptional.isPresent() && wallet.getPin().equals(pinOptional.get().getParamValue()))) {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The pin is incorrect");
        }
    }
}
