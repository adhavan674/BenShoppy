package com.adhavan.benshoppy.globalexception.customexception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException(String message) {
        super(message);
    }
}
