package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.Category;
import com.adhavan.benshoppy.entity.Product;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByUserAndStatus(User user, Status status);

    List<Product> findByUserAndNameContaining(User user,String name);


    @Query(value = "select * from product_table order by created_at desc limit 10",nativeQuery = true)
    List<Product> getLatest();

    @Query(value = "select * from product_table where user_id =:id order by created_at desc limit 5",nativeQuery = true)
    List<Product> getLatestForSeller(Long id);

    List<Product> findByCategory(Category category);


    List<Product> findTop7ByNameContaining(String name);


    List<Product> findTop7ByNameContainingOrCategory(String name, Category category);


    Page<Product> findByNameContaining(String name, Pageable pageable);

    Long countByStatus(Status status);


    @Query(value = " select distinct name from product_table where name like CONCAT(:name,'%') limit 10 "
            ,nativeQuery = true )
    List<String> textSuggest(@Param("name") String name);

    List<Product> findByUser(User user);

    Long countByUser(User user);

    Long countByUserAndStatus(User user, Status status);
}
