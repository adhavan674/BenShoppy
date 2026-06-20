package com.adhavan.benshoppy.dto.product;

import com.adhavan.benshoppy.entity.Category;
import com.adhavan.benshoppy.entity.ProductImage;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DetailsProductResponse {

    private Long product_id;
    private String name;
    private Double price;
    private Integer stock;
    private String description;
    private Status status;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private String seller_name;
    private String category_name;
    private Long category_id;
    private List<ProductImageResponse> images;

}
