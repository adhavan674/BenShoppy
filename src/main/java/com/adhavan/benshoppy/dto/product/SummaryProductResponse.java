package com.adhavan.benshoppy.dto.product;


import lombok.Data;

@Data
public class SummaryProductResponse {

    private Long id;
    private String name;
    private Double price;
    private String thumbnail;

}
