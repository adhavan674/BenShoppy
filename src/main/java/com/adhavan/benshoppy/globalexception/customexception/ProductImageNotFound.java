package com.adhavan.benshoppy.globalexception.customexception;

public class ProductImageNotFound extends RuntimeException {
  public ProductImageNotFound(String message) {
    super(message);
  }
}
