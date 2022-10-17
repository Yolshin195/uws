package com.provider.uws.handler;

import com.provider.uws.GenericArguments;

public class RequestHandler<A extends GenericArguments> {
    private A arguments;

    public RequestHandler(A arguments) {
        this.arguments = arguments;
    }

    public A getParams() {
        return arguments;
    }
}
