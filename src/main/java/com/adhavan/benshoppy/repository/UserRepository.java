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

    Long countByStatusAndRole(Status status, Role role);

    List<User> findByRole(Role role);
}
