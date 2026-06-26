package com.adhavan.benshoppy.dto.order;

import com.adhavan.benshoppy.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateOrderStatus {

    @NotNull
    private OrderStatus orderStatus;

}
