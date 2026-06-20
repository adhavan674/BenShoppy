package com.adhavan.benshoppy.dto.product;


import com.adhavan.benshoppy.dto.category.CategoryResponse;
import com.adhavan.benshoppy.entity.Category;
import lombok.Data;

@Data
public class SummaryProductResponse {

    private Long id;
    private String name;
    private Double price;
    private String thumbnail;
   // private CategoryResponse category;

}
