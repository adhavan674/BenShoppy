package com.adhavan.benshoppy.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {

    @NotBlank(message = "Name must Enter valid or Should not be null")
    private String name;

}
