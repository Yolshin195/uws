package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.model.Wallet;

import java.util.List;

public interface VerificationService {
    boolean isValidLuhn(String value);
    boolean isValidPhone(String phoneNumber);
}
