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

    @Query(value = "select * from product_table where status ='ACTIVE' order by created_at desc limit 10",nativeQuery = true)
    List<Product> getLatest();

    @Query(value = "select * from product_table where user_id =:id order by created_at desc limit 5",nativeQuery = true)
    List<Product> getLatestForSeller(Long id);

    Long countByStatus(Status status);

    @Query(value = " select distinct name from product_table where name like CONCAT(:name,'%') limit 10 "
            ,nativeQuery = true )
    List<String> textSuggest(@Param("name") String name);

    Page<Product> findByUser(User user, Pageable pageable);
    List<Product> findByUser(User user);
    Long countByUser(User user);

    Long countByUserAndStatus(User user, Status status);

    List<Product> findByCategoryAndStatus(Category category, Status status);

    Page<Product> findByNameContainingIgnoreCaseAndStatus(String name, Pageable pageable, Status status);

    @Query(value = "select * from product_table where lower(name) like (CONCAT(:search,'%')) and id != :product_Id limit 5", nativeQuery = true)
    List<Product> findRecommendProduct(String search, Long product_Id);

    @Query(value = "select  * from product_table where lower(name) like ((CONCAT(:search,'%')) or category_id = :category_id)) and id != :product_id limit 5" , nativeQuery = true)
    List<Product> findRecommendProductWithCategory(String search,Long category_id, Long product_Id);
}
