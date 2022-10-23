package com.provider.uws.service;

import com.provider.uws.GenericArguments;
import com.provider.uws.model.User;
import com.provider.uws.service.bd.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserService userService;

    public boolean check(String username, String password) {
        User user = userService.findByUsername(username);
        return user != null && user.getActive() && user.getPassword().equals(password);
    }

    public void authentication(GenericArguments arguments) {
        if (!check(arguments.getUsername(), arguments.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The username or password you entered is incorrect");
        }
    }

}
