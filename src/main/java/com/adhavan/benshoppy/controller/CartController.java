package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.cart.CreateCartRequest;
import com.adhavan.benshoppy.dto.cart.FinalCartResponse;
import com.adhavan.benshoppy.dto.cart.UpdateCartRequest;
import com.adhavan.benshoppy.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;



    @PostMapping("/{user_id}/{product_id}")
    public String addProductToCart(@Valid @RequestBody CreateCartRequest dto,
                                   @PathVariable Long user_id,
                                   @PathVariable Long product_id){
       cartService.addProductToCart(dto,user_id,product_id);
       return " added to cart ";

    }


    @PatchMapping("/{cart_item_id}")
    public String updateQuantity(@Valid @RequestBody UpdateCartRequest dto, @PathVariable Long cart_item_id){
        cartService.updateQuantity(dto,cart_item_id);
        return "quantity updated ";
    }

    @DeleteMapping("/{cart_item_id}")
    public String removeProduct(@PathVariable Long cart_item_id){
        cartService.removeProduct(cart_item_id);
        return "delete successful";
    }


    @DeleteMapping("/all/{user_id}")
    public String removeAllProduct(@PathVariable Long user_id){
        cartService.removeAllProduct(user_id);
        return "all product removed from cart";
    }


    @GetMapping("/{user_id}")
    public FinalCartResponse getCart(@PathVariable Long user_id){
       return cartService.getCartDetails(user_id);
    }

}
