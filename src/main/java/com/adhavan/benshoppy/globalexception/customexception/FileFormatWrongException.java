package com.adhavan.benshoppy.globalexception.customexception;

public class FileFormatWrongException extends RuntimeException {
    public FileFormatWrongException(String message) {
        super(message);
    }
}
