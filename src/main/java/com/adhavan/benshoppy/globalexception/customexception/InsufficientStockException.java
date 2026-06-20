package com.adhavan.benshoppy.globalexception.customexception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
