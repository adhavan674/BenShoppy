package com.adhavan.benshoppy.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateCategoryRequest {



    private String name;
    private MultipartFile image;

}
