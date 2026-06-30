package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.order.SummaryOrderResponse;
import com.adhavan.benshoppy.dto.order.UpdateOrderStatus;
import com.adhavan.benshoppy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/order/{user_id}/{address_id}")
    public String createOrder(@PathVariable Long user_id, @PathVariable Long address_id) {
        log.info("request reached in controller");
        log.info("service method call");
        orderService.createOrder(user_id, address_id);

        return "order placed success";

    }


    @PatchMapping("/order/{order_item_id}")
    public String cancelOrder(@PathVariable Long order_item_id) {

        orderService.cancelOrder(order_item_id);
        return "order cancelled ";
    }

    @GetMapping("/order/{user_id}/summary")
    public List<SummaryOrderResponse> getOrdersForUser(@PathVariable Long user_id) {

        return orderService.getOrders(user_id);

    }


    @GetMapping("/seller/order/{seller_id}")
    public List<SummaryOrderResponse> getOrdersForSeller(@PathVariable Long seller_id) {

        return orderService.getSellerOrders(seller_id);

    }


    @PatchMapping("/seller/{order_item_id}")
    public String updateOrderStatus(@PathVariable Long order_item_id, @RequestBody UpdateOrderStatus dto) {

        orderService.updateOrderStatus(order_item_id, dto);
        return "updated order status";
    }

    @GetMapping("/admin/{user_id}/orders")
    public List<SummaryOrderResponse> getOrdersAdmin(@PathVariable Long user_id) {
       return orderService.getOrders(user_id);
    }

}
