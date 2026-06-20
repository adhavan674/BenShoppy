package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.User;
import com.adhavan.benshoppy.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchListRepository extends JpaRepository<WatchList,Long> {

    List<WatchList> findByUser(User user);

    void deleteByUser(User user);
}
