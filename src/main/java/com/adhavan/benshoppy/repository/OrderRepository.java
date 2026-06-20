package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.Order;
import com.adhavan.benshoppy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {


    List<Order> findByUser(User user);

}
