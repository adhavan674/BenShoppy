package com.adhavan.benshoppy.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCategoryRequest {

    @NotBlank(message = "Name must Enter valid or Should not be null")
    private String name;
    @NotNull
    private MultipartFile img;

}
