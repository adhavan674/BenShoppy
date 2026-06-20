package com.adhavan.benshoppy.mapper;

import com.adhavan.benshoppy.dto.product.CreateProductRequest;
import com.adhavan.benshoppy.dto.product.DetailsProductResponse;
import com.adhavan.benshoppy.dto.product.ProductImageResponse;
import com.adhavan.benshoppy.dto.product.SummaryProductResponse;
import com.adhavan.benshoppy.entity.Product;
import com.adhavan.benshoppy.entity.ProductImage;
import com.adhavan.benshoppy.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product CreateProductToProduct(CreateProductRequest dto);
    SummaryProductResponse productToSummary(Product product);
    DetailsProductResponse productToDetails(Product product);
    ProductImageResponse productImageToDto(ProductImage img);
}
