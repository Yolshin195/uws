package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.model.Wallet;

import java.util.List;

public interface VerificationService {
    void checkPin(Wallet wallet, List<GenericParam> paramList);
    boolean isValidLuhn(String value);
}
