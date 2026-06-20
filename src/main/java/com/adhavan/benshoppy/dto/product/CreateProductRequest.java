package com.adhavan.benshoppy.dto.product;

import com.adhavan.benshoppy.entity.Category;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateProductRequest {

    @NotBlank
    private String name;
    @Positive
    @Min(1)
    @NotNull
    private Double price;
    @Positive
    @Min(0)
    @NotNull
    private Integer stock;
    @NotBlank
    @Size(min = 5 ,max = 500)
    private String description;
    @NotNull
    private Long category_id;
    @NotNull
    private Long user_id;

}
