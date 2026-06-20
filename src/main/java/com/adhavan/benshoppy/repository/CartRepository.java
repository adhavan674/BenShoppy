package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.Cart;
import com.adhavan.benshoppy.entity.CartItem;
import com.adhavan.benshoppy.entity.User;
import org.hibernate.sql.exec.spi.JdbcCallParameterExtractor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long>  {


    Cart findByUser(User user);

}
