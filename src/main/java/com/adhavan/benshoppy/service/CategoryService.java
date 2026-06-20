package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.dto.category.CategoryImageResponse;
import com.adhavan.benshoppy.dto.category.CategoryResponse;
import com.adhavan.benshoppy.dto.category.CreateCategoryRequest;
import com.adhavan.benshoppy.dto.category.UpdateCategoryRequest;
import com.adhavan.benshoppy.entity.Category;
import com.adhavan.benshoppy.globalexception.customexception.FileFormatWrongException;
import com.adhavan.benshoppy.globalexception.customexception.ResourceAlreadyExistsException;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.mapper.CategoryMapper;
import com.adhavan.benshoppy.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategory(CreateCategoryRequest dto, MultipartFile img) throws IOException {

        Optional<Category> cat = categoryRepository.findByName(dto.getName());

     if(cat.isPresent()){
         throw new ResourceAlreadyExistsException(" " + dto.getName() + " : Category already exist ");
     }

     String contentType = img.getContentType();
     if(contentType == null || !contentType.startsWith("image/") ){
         throw new FileFormatWrongException(" upload image format only or upload image ");
     }
     Category category =  categoryMapper.createCategoryRequestToCategory(dto);


     String folder ="uploads/categoryImage";
     String filename = UUID.randomUUID() + "_" + img.getOriginalFilename();

      Path path = Paths.get(folder,filename);  //   uploads/categoryImage/khfgsdhfg_adhav.jpg
      img.transferTo(path);

     category.setUrl("/categoryImage/"+ filename);  //  category/efhidgd_adhav.jpg
     categoryRepository.save(category);

    }

    public List<CategoryResponse> getCategories() {

       List<Category> categories = categoryRepository.findAll();
       return  categories.stream().map(categoryMapper::categoryToCategoryResponse)
               .toList();
    }

    public void updateCategory(UpdateCategoryRequest dto, Long id,MultipartFile img) throws IOException {

        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + id + " : Category not found"));

        if(img!=null && !img.isEmpty()){

            String contentType = img.getContentType();
            if(contentType == null || !contentType.startsWith("image/") ){
                throw new FileFormatWrongException(" upload image format only or upload image ");

            }


        String folder = "uploads/categoryImage" ;
        String fileName = category.getUrl().substring(9);
        Path path = Paths.get(folder,fileName); // uploads/categoryImage  /category/hgsjdfgd_adhav.pgb

        Files.deleteIfExists(path);

        String newFileName = UUID.randomUUID() + "_" + img.getOriginalFilename();
        String newFolder = "uploads/categoryImage";
        Path path1 = Paths.get(newFolder,newFileName);
        img.transferTo(path1);

        category.setUrl("/categoryImage/"+ newFileName);
        }
        category.setName(dto.getName());

       categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + id + " : Category not found"));
        categoryRepository.deleteById(id);
    }

    public List<CategoryImageResponse> getCategoriesWithImages() {
        List<Category> category = categoryRepository.findAll();
       return category.stream().map(categoryMapper::categoryToCategoryImage).toList();
    }
}
