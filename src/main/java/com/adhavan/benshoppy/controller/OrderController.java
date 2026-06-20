package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.order.SummaryOrderResponse;
import com.adhavan.benshoppy.dto.order.UpdateOrderStatus;
import com.adhavan.benshoppy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/order/{user_id}/{address_id}") // ok
    public String createOrder(@PathVariable Long user_id,@PathVariable Long address_id){
      log.info("request reached in controller");
      log.info("service method call");
      orderService.createOrder(user_id,address_id);

      return "order placed success";

    }

    @PatchMapping("/order/{id}") //ok
    public String cancelOrder(@PathVariable Long id){

        orderService.cancelOrder(id);
        return "order cancelled ";
    }

    @GetMapping("/order/{id}/summary") // user ok
    public List<SummaryOrderResponse> getOrders(@PathVariable Long id){

        return orderService.getOrders(id);
        
    }

    @GetMapping("/seller/order/{id}")
    public List<SummaryOrderResponse>  getOrdersForSeller(@PathVariable Long id){

        return orderService.getSellerOrders(id);

    }

    @PatchMapping("/seller/{id}")
    public String updateOrderStatus(@PathVariable Long id,@RequestBody UpdateOrderStatus dto){

         orderService.updateOrderStatus(id,dto);
         return  "updated order status";
    }



}
