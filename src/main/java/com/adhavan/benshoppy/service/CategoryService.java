package com.adhavan.benshoppy.service;

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

    public void addCategory(CreateCategoryRequest dto) throws IOException {

        Optional<Category> cat = categoryRepository.findByName(dto.getName());

     if(cat.isPresent()){
         throw new ResourceAlreadyExistsException(" " + dto.getName() + " : Category already exist ");
     }

     String contentType = dto.getImage().getContentType();
     if(contentType == null || !contentType.startsWith("image/") ){
         throw new FileFormatWrongException(" upload image format only or upload image ");
     }
     Category category =  categoryMapper.createCategoryRequestToCategory(dto);


     String folder ="images/categoryImage";
     String filename = UUID.randomUUID() + "_" + dto.getImage().getOriginalFilename();

      Path path = Paths.get(folder,filename);  //   images/categoryImage/khfgsdhfg_adhav.jpg
      dto.getImage().transferTo(path);

     category.setUrl("/categoryImage/"+ filename);  //  categoryImage/efhidgd_adhav.jpg
     categoryRepository.save(category);

    }

    public void updateCategory(Long id , UpdateCategoryRequest dto) throws IOException {

        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + id + " : Category not found"));

        if(dto.getName()!=null && !dto.getName().isEmpty()) {
            category.setName(dto.getName());
        }

        if(dto.getImage()!=null && !dto.getImage().isEmpty()){

            String contentType = dto.getImage().getContentType();

            if(contentType == null || !contentType.startsWith("image/") ){
                throw new FileFormatWrongException(" upload image format only or upload image ");
            }


        String folder = "images" ;
        String fileName = category.getUrl();
        Path path = Paths.get(folder,fileName); // images + /categoryImage/hgsjdfgd_adhav.pgb

        Files.deleteIfExists(path);

        String newFileName = UUID.randomUUID() + "_" + dto.getImage().getOriginalFilename();
        String newFolder = "images/categoryImage";
        Path path1 = Paths.get(newFolder,newFileName);
        dto.getImage().transferTo(path1);

        category.setUrl("/categoryImage/"+ newFileName);

        }

       categoryRepository.save(category);

    }

    public void deleteById(Long id) throws IOException {
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + id + " : Category not found"));

        String path1 = "images";
        String path2 = category.getUrl(); //  /categoryImage/wjgdwjg.jpg
        Path path = Paths.get(path1,path2);
        Files.deleteIfExists(path);
        categoryRepository.deleteById(id);
    }

    public List<CategoryResponse> getCategoriesWithImages() {
        List<Category> category = categoryRepository.findAll();
       return category.stream().map(categoryMapper::categoryToCategoryResponse).toList();
    }
}
