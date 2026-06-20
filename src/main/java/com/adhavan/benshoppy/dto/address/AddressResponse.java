package com.adhavan.benshoppy.dto.address;

import lombok.Data;

@Data
public class AddressResponse {

    private Long id;
    private String address;
    private Long number;
    private Integer pinCode;

}
