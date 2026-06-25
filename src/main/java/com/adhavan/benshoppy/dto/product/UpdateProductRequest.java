package com.adhavan.benshoppy.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile thumbnail;

}
