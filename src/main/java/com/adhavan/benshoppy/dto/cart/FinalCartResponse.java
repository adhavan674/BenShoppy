package com.adhavan.benshoppy.dto.cart;

import lombok.Data;

import java.util.List;

@Data
public class FinalCartResponse {
    private List<OneCartSetup> oneCartSetup;
    private Double totalPrice;
}
