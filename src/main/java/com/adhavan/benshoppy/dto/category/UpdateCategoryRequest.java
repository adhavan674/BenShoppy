package com.adhavan.benshoppy.dto.category;


import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateCategoryRequest {

    private String name;
    private MultipartFile image;

}
