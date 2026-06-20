package com.adhavan.benshoppy.globalexception.customexception;

public class RequestImageNotFound extends RuntimeException {

    public RequestImageNotFound(String msg) {
        super(msg);

    }
}