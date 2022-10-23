package com.provider.uws.service;

import com.provider.uws.GenericArguments;

public interface AuthenticationService {
    boolean check(String username, String password);
    void authentication(GenericArguments arguments);
}
