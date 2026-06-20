package com.adhavan.benshoppy.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateProductRequest {


    @Pattern(regexp = "^[A-Za-z ]+$")
    private String name;
    @Positive
    @Min(1)
    private Double price;
    
    @Positive
    private Integer stock;

    @Size(min = 5 ,max = 50)
    private String description;

    private Long category_id;

}
