package com.adhavan.benshoppy.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.tool.schema.extract.spi.ExtractionContext;

@Data
public class UpdateProductPrice {

    @NotNull
    @Positive
    @Min(1)
    private Double price;
}
