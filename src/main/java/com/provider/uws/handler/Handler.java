package com.provider.uws.handler;

import com.provider.uws.GenericArguments;
import com.provider.uws.GenericParam;
import com.provider.uws.GenericResult;

public interface Handler  {
    void setNext(Handler handler);
    void handle(RequestHandler request);
}
