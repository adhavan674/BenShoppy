package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.Order;
import com.adhavan.benshoppy.entity.OrderItem;
import com.adhavan.benshoppy.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    List<OrderItem> findByOrder(Order order);
    Page<OrderItem> findByOrder(Order order, Pageable pageable);
    List<OrderItem> findByProductIn(List<Product> products);

}
