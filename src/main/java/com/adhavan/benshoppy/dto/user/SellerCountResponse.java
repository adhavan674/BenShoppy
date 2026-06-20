package com.adhavan.benshoppy.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellerCountResponse {
    private Long totalProducts;
    private Long activeProducts;
    private Long inactiveProducts;
 //   private Long totalOrders;
}
