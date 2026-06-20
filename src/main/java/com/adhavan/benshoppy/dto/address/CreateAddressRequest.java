package com.adhavan.benshoppy.dto.address;

import com.adhavan.benshoppy.entity.User;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateAddressRequest {


    @Pattern(regexp = "^[0-9]{10}$",message = " number is invalid , enter proper 10 digits number")
    private Long number;

    @NotBlank
    @Size(min = 15,message = " enter address correctly")
    private String address;

    @Pattern(regexp = "^[0-9]{6}$", message = " pin code must enter correctly")
    private Integer pinCode;

}
