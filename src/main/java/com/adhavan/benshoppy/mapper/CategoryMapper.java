package com.adhavan.benshoppy.mapper;

import com.adhavan.benshoppy.dto.category.CategoryImageResponse;
import com.adhavan.benshoppy.dto.category.CategoryResponse;
import com.adhavan.benshoppy.dto.category.CreateCategoryRequest;
import com.adhavan.benshoppy.dto.category.UpdateCategoryRequest;
import com.adhavan.benshoppy.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category createCategoryRequestToCategory(CreateCategoryRequest dto);
    CategoryResponse categoryToCategoryResponse(Category category);
    CategoryImageResponse categoryToCategoryImage(Category category);
}
