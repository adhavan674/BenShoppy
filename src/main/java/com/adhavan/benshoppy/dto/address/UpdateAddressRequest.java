package com.adhavan.benshoppy.dto.address;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAddressRequest {

    @Pattern(regexp = "^[0-9]{10}$",message = " number is invalid , enter proper 10 digits number")
    private Long number;

    @NotEmpty
    @Size(min = 15,message = " enter address correctly")
    private String address;

    @Pattern(regexp = "^[0-9]{6}$", message = " pin code must enter correctly")
    private Integer pinCode;

}
