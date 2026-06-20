package com.adhavan.benshoppy.dto.cart;

import lombok.Data;

@Data
public class OneCartSetup {

    private Long id;
    private CartProductSetup productResponse;
    private Integer quantity;
    private Double subTotal;
}
