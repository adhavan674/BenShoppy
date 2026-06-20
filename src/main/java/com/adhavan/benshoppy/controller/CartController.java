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


    @PostMapping()
    public String addProductToCart(@Valid @RequestBody CreateCartRequest dto){
       cartService.addProductToCart(dto);
       return dto.getProduct_id()+ " added to cart ";

    }

    @PatchMapping("/{id}")
    public String updateQuantity(@Valid @RequestBody UpdateCartRequest dto, @PathVariable Long id){
        cartService.updateQuantity(dto,id);
        return "quantity updated ";
    }

    @DeleteMapping("/{id}")
    public String removeProduct(@PathVariable Long id){
        cartService.removeProduct(id);
        return "delete successful";
    }

    @DeleteMapping("/{id}/all")
    public String removeAllProduct(@PathVariable Long id){
        cartService.removeAllProduct(id);
        return "all product removed from cart";
    }

    @GetMapping("/{id}")
    public FinalCartResponse getCart(@PathVariable Long id){
       return cartService.getCartDetails(id);
    }

}
