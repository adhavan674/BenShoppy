package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.product.ProductImageResponse;
import com.adhavan.benshoppy.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ProductImageController {


        @Autowired
        private ProductImageService productImageService;

        @DeleteMapping("/{id}")
        public String deleteImage(@PathVariable Long id){

            productImageService.deleteImage(id);

            return "deleted successful of image";
        }


        @PatchMapping("/{id}")
        public String updateImage(@PathVariable Long id,
                                  @RequestParam MultipartFile image)
                throws IOException {


            productImageService.updateImage(id,image);

            return "Update successfully";
        }


        @GetMapping("/{id}")
        public List<ProductImageResponse> getAllImage(@PathVariable Long id){

            return productImageService.getAllImage(id);
        }


        @PostMapping("/{id}")
        public String addImages(@PathVariable Long id,
                                @RequestParam List<MultipartFile> images)
                throws IOException {

            productImageService.addImages(id,images);

            return "Product Images Added";
        }
    }



