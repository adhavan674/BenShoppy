package com.adhavan.benshoppy.globalexception.customexception;

public class MaxLimitForImage extends RuntimeException {
  public MaxLimitForImage(String message) {
    super(message);
  }
}
