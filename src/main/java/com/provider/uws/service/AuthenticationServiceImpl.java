package com.provider.uws.service;

import com.provider.uws.model.User;
import com.provider.uws.service.bd.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserService userService;

    public boolean check(String username, String password) {
        User user = userService.findByUsername(username);
        if (user != null && user.getActive() && user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }
}
