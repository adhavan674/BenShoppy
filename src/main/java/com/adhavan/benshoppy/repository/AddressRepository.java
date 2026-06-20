package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.Address;
import com.adhavan.benshoppy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByUser(User user);
}
