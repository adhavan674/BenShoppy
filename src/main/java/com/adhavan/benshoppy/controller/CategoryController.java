package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.category.*;
import com.adhavan.benshoppy.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;




    @PostMapping(value = "/admin/category" ,
             consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addCategory(@ModelAttribute @Valid CreateCategoryRequest dto) throws IOException {

        categoryService.addCategory(dto);

        return "Added Success";

    }




    @GetMapping("/public/categories")
    public List<CategoryResponse> getAllCategoryWithImage(){

        return categoryService.getCategoriesWithImages();

    }




    @PatchMapping(value = "/admin/category/{category_id}",
    consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE )
    public String updateCategory(@Valid @ModelAttribute UpdateCategoryRequest dto ,
            @PathVariable Long category_id) throws IOException {

        categoryService.updateCategory(category_id , dto);

        return "Updated Success";

    }





    @DeleteMapping("/admin/category/{category_id}")
    public String deleteCategory(@PathVariable Long category_id){
        categoryService.deleteById(category_id);

        return "category deleted successFully";

    }


}
