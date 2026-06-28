package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.Role;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByMail(String email);

    Long countByRole(Role role);

    List<User> findByNameAndRole(String name, Role role);

    List<User> findByStatusAndRole(Status status, Role role);

    Long countByStatusAndRole(Status status, Role role);
}
