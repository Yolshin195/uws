package com.provider.uws.handler;

import com.provider.uws.GenericArguments;
import com.provider.uws.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class AuthenticationHandler extends BaseHandler {

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public void handle(RequestHandler requestHandler) {
        GenericArguments arguments = requestHandler.getParams();
        if (authenticationService.check(arguments.getUsername(), arguments.getPassword())) {
            if (next != null) {
                next.handle(requestHandler);
                return;
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The username or password you entered is incorrect");
    }
}
