package com.provider.uws.handler;


public abstract class BaseHandler implements Handler {
    Handler next;

    @Override
    public void setNext(Handler handler) {
        this.next = handler;
    }

    @Override
    public abstract void handle(RequestHandler request);
}
