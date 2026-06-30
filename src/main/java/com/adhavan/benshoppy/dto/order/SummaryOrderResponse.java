package com.adhavan.benshoppy.dto.order;

import com.adhavan.benshoppy.dto.address.AddressResponse;
import com.adhavan.benshoppy.entity.OrderStatus;
import com.adhavan.benshoppy.entity.Payment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SummaryOrderResponse {

    private Long product_id;
    private Long orderItem_id;
    private String img;
    private String productName;
    private Integer quantity;
    private Double price;
    private OrderStatus status;
    private Payment payment;
    private AddressResponse address;
    private LocalDateTime placedAt;

}
