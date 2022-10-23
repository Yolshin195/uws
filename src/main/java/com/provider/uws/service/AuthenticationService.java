package com.provider.uws.service;

import com.provider.uws.GenericArguments;
import com.provider.uws.GenericParam;
import com.provider.uws.model.Wallet;

import java.util.List;

public interface AuthenticationService {
    boolean check(String username, String password);

    void checkPin(Wallet wallet, List<GenericParam> paramList);
    void authentication(GenericArguments arguments);
}
