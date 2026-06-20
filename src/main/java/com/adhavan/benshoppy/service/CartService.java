package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.dto.cart.*;
import com.adhavan.benshoppy.entity.Cart;
import com.adhavan.benshoppy.entity.CartItem;
import com.adhavan.benshoppy.entity.Product;
import com.adhavan.benshoppy.entity.User;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.repository.CartItemRepository;
import com.adhavan.benshoppy.repository.CartRepository;
import com.adhavan.benshoppy.repository.ProductRepository;
import com.adhavan.benshoppy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

   @Autowired
   private CartRepository cartRepository;

   @Autowired
   private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public void addProductToCart(CreateCartRequest dto) {

    User user = userRepository.findById(dto.getUser_id())
            .orElseThrow(() -> new ResourceNotFoundException(" User not found"));
    Cart cart =  cartRepository.findByUser(user);
    Product product = productRepository.findById(dto.getProduct_id())
            .orElseThrow(() -> new ResourceNotFoundException(" " + dto.getProduct_id() + " Product id not found"));
    Integer quantity = dto.getQuantity();

    CartItem cartItem = new CartItem();
    cartItem.setProduct(product);
    cartItem.setQuantity(quantity);
    cartItem.setCart(cart);

    cartItemRepository.save(cartItem);

    }

    public void updateQuantity(UpdateCartRequest dto, Long id) {

      CartItem cartItem = cartItemRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException(" " + id + " Cart item not found "));
      cartItem.setQuantity(dto.getQuantity());
      cartItemRepository.save(cartItem);
    }

    public void removeProduct(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + id + " Cart item not found "));
        cartItemRepository.deleteById(id);
    }

    @Transactional
    public void removeAllProduct(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + id + " user id not found "));
        Cart cart = cartRepository.findByUser(user);
        cartItemRepository.deleteByCart(cart);
    }

    public FinalCartResponse getCartDetails(Long id){
      User user = userRepository.findById(id)
              .orElseThrow(() ->  new ResourceNotFoundException(" " +  id + " user not found"));
      Cart cart = cartRepository.findByUser(user);
      List<CartItem> cartItems = cartItemRepository.findByCart(cart);
     // CartItem item = cartItems.get(0);

      FinalCartResponse finalCartResponse = new FinalCartResponse();
      Double totalPrice = 0.00;

      List<OneCartSetup> multipSetup = new ArrayList<>();

      for (CartItem cartItem : cartItems){
      // product setup
       CartProductSetup product = new CartProductSetup();
       product.setId(cartItem.getProduct().getId());
       product.setName(cartItem.getProduct().getName());
       product.setPrice(cartItem.getProduct().getPrice());
       product.setThumbnail(cartItem.getProduct().getThumbnail());
       // one full cart details
       OneCartSetup oneCartSetup = new OneCartSetup();
       oneCartSetup.setId(cartItem.getId());
       oneCartSetup.setQuantity(cartItem.getQuantity());
       oneCartSetup.setProductResponse(product);
       oneCartSetup.setSubTotal(cartItem.getProduct().getPrice()*cartItem.getQuantity());

       // add one cart details to list
       multipSetup.add(oneCartSetup);

       // add all product price
       totalPrice = totalPrice + oneCartSetup.getSubTotal();
      }
      finalCartResponse.setOneCartSetup(multipSetup);
      finalCartResponse.setTotalPrice(totalPrice);


    return finalCartResponse;

    }


}
