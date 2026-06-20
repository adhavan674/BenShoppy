package com.adhavan.benshoppy.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AdminCountResponse {

    private Long totalProduct;
    private Long totalSeller;
    private Long totalUser;
    private Long totalCategory;
    private Long activeProduct;
    private Long inactiveProduct;
    private Long activeUser;
    private Long blockedUser;
    private Long activeSeller;
    private Long BlockedSeller;
    private Long totalOrders;
}
