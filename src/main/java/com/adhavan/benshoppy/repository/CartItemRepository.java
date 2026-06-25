package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.Cart;
import com.adhavan.benshoppy.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByCart(Cart cart);

    void deleteByCart(Cart cart);


}
