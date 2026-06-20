package com.adhavan.benshoppy.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

     private Long address_id;
     private Long user_id;
}
