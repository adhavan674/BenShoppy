package com.adhavan.benshoppy.repository;

import com.adhavan.benshoppy.entity.Product;
import com.adhavan.benshoppy.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

    List<ProductImage> findByProduct(Product product);

    @Query(value = "select count(*) from product_image where product_id = :id", nativeQuery = true)
    Long countByProductId(@Param("id") Long id);

    void deleteByProduct(Product product);
}
