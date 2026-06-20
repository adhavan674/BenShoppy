package com.adhavan.benshoppy.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCartRequest {

    @NotBlank
    @Min(1)
    private Integer quantity;
}
