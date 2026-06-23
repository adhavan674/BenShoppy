package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.order.SummaryOrderResponse;
import com.adhavan.benshoppy.dto.order.UpdateOrderStatus;
import com.adhavan.benshoppy.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Subclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Order APIs")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create order")
    @PostMapping("/order/{user_id}/{address_id}") // ok
    public String createOrder(@PathVariable Long user_id,@PathVariable Long address_id){
      log.info("request reached in controller");
      log.info("service method call");
      orderService.createOrder(user_id,address_id);

      return "order placed success";

    }

    @Operation(summary = "Cancel Order")
    @PatchMapping("/order/{order_item_id}") //ok
    public String cancelOrder(@PathVariable Long order_item_id){

        orderService.cancelOrder(order_item_id);
        return "order cancelled ";
    }

    @Operation(summary = "Get orders ")
    @GetMapping("/order/{user_id}/summary") // user ok
    public List<SummaryOrderResponse> getOrders(@PathVariable Long user_id){

        return orderService.getOrders(user_id);
        
    }

    @Operation(summary = "get orders for seller products")
    @GetMapping("/seller/order/{seller_id}")
    public List<SummaryOrderResponse>  getOrdersForSeller(@PathVariable Long seller_id){

        return orderService.getSellerOrders(seller_id);

    }

    @Operation(summary = "update OrderItem Status")
    @PatchMapping("/seller/{order_item_id}")
    public String updateOrderStatus(@PathVariable Long order_item_id,@RequestBody UpdateOrderStatus dto){

         orderService.updateOrderStatus(order_item_id,dto);
         return  "updated order status";
    }



}
