package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.category.CategoryImageResponse;
import com.adhavan.benshoppy.dto.category.CategoryResponse;
import com.adhavan.benshoppy.dto.category.CreateCategoryRequest;
import com.adhavan.benshoppy.dto.category.UpdateCategoryRequest;
import com.adhavan.benshoppy.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // add category
    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute @Valid CreateCategoryRequest dto,
                              @RequestParam MultipartFile img) throws IOException {

        categoryService.addCategory(dto,img);

        return "Added Success";

    }
    // get all categories
    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategory(){

      return categoryService.getCategories();

    }

    // get all categories
    @GetMapping("/public/categories/image")
    public List<CategoryImageResponse> getAllCategoryWithImage(){

        return categoryService.getCategoriesWithImages();

    }

    // update category name
    @PatchMapping("/category/{id}")
    public String updateCategory(@Valid @ModelAttribute UpdateCategoryRequest dto ,
            @RequestParam(required = false) MultipartFile img, @PathVariable Long id) throws IOException {

        categoryService.updateCategory(dto,id,img);

        return "Updated Success";

    }

    // not recommended

    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);

        return "category deleted successFully";

    }


}
