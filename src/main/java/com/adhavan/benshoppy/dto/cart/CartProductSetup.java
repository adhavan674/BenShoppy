package com.adhavan.benshoppy.dto.cart;

import lombok.Data;

@Data
public class CartProductSetup {

    private Long id;
    private String name;
    private Double price;
    private String thumbnail;

}
