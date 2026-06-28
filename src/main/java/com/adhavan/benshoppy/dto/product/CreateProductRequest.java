package com.adhavan.benshoppy.dto.product;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class CreateProductRequest {

    @NotBlank
    private String name;
    @Positive
    @NotNull
    private Double price;
    @PositiveOrZero
    @NotNull
    private Integer stock;
    @NotBlank
    @Size(min = 5 ,max = 500)
    private String description;
    @NotNull
    private MultipartFile thumbnail;
    @NotEmpty
    @Size(min =  3)
    private List<MultipartFile> images;

}
