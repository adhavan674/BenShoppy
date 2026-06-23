package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.category.*;
import com.adhavan.benshoppy.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Category APIs")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // add category
    @Operation(summary = "Add Category")
    @PostMapping(value = "/admin/category" ,
             consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addCategory(@ModelAttribute @Valid CreateCategoryRequest dto) throws IOException {

        categoryService.addCategory(dto);

        return "Added Success";

    }


    // get all categories with images
    @Operation(summary = "Get All Categories")
    @GetMapping("/public/categories")
    public List<CategoryResponse> getAllCategoryWithImage(){

        return categoryService.getCategoriesWithImages();

    }

    // update category name and img

    @Operation(summary = "Update Category", description = " Can update Only name or only image or both , Dont use Tick for Image")
    @PatchMapping(value = "/admin/category/{id}",
    consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE )
    public String updateCategory(@Valid @ModelAttribute UpdateCategoryRequest dto ,
            @PathVariable Long id) throws IOException {

        categoryService.updateCategory(id, dto);

        return "Updated Success";

    }


    // delete category only  if no relation

    @Operation(summary = "Delete Category" , description = "Always delete the No relation Category")
    @DeleteMapping("/admin/category/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);

        return "category deleted successFully";

    }


}
