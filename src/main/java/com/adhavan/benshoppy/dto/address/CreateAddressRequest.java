package com.adhavan.benshoppy.dto.address;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateAddressRequest {


    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @NotNull
    private Long number;

    @NotBlank
    @Size(min = 15,message = " enter address correctly")
    private String address;

    @Min(value = 100000)
    @Max(value = 999999)
    @NotNull
    private Integer pinCode;

}
