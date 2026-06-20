package com.adhavan.benshoppy.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCartRequest {

    private Long user_id;
    @NotNull
    private Long product_id;
    @NotNull
    @Min(1)
    private Integer quantity;

}
