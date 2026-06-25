package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.product.ProductImageResponse;
import com.adhavan.benshoppy.service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ProductImage APIs" , description = "Seller only can access this APIs")
@RestController
@RequestMapping("/images")
public class ProductImageController {


        @Autowired
        private ProductImageService productImageService;

        @Operation(summary = "Delete one image for a Product")
        @DeleteMapping("/{product_image_id}")
        public String deleteImage(@PathVariable Long product_image_id) throws IOException {

            productImageService.deleteImage(product_image_id);

            return "deleted successful of image";
        }


        @Operation(summary = "Change image for a Product ")
        @PatchMapping(value = "/{product_image_id}" ,
                consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
        public String updateImage(@PathVariable Long product_image_id,
                                  @ModelAttribute MultipartFile image)
                throws IOException {


            productImageService.updateImage(product_image_id,image);

            return "Update successfully";
        }

        @Operation(summary = "Get All images of a Product")
        @GetMapping("/{product_id}")
        public List<ProductImageResponse> getAllImage(@PathVariable Long product_id){

            return productImageService.getAllImage(product_id);
        }

        @Operation(summary = "Add images To Existing Product")
        @PostMapping(value = "/{product_id}" ,
                consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
        public String addImages(@PathVariable Long product_id,
                                @ModelAttribute List<MultipartFile> images)
                throws IOException {

            productImageService.addImages(product_id ,images);

            return "Product Images Added";
        }
    }



