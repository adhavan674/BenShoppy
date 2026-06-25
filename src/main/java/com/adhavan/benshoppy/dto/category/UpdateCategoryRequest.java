package com.adhavan.benshoppy.dto.category;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateCategoryRequest {



    private String name;
    private MultipartFile image;

}
