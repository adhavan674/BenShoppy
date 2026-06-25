package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.dto.product.ProductImageResponse;
import com.adhavan.benshoppy.entity.Product;
import com.adhavan.benshoppy.entity.ProductImage;
import com.adhavan.benshoppy.globalexception.customexception.*;
import com.adhavan.benshoppy.mapper.ProductMapper;
import com.adhavan.benshoppy.repository.ProductImageRepository;
import com.adhavan.benshoppy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public void deleteImage(Long id) throws IOException {

        ProductImage productImage =
                productImageRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductImageNotFound("image not found"));
        String filename = productImage.getUrl();
        String folder = "uploads";
        Path path = Paths.get(folder,filename);
        Files.deleteIfExists(path);

        productImageRepository.deleteById(id);
    }

    public void updateImage(Long id,
                            MultipartFile image)
            throws IOException {

        ProductImage productImage =
                productImageRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductImageNotFound("image not found"));


        String contentType = image.getContentType();
        if(contentType == null || !contentType.startsWith("image/") ){
            throw new FileFormatWrongException(" upload image format only or upload image ");
        }


        String folder = "uploads/productImage";
        String oldFileName = productImage.getUrl().substring(13);
        Path path = Paths.get(folder,oldFileName);
        Files.deleteIfExists(path);



        String imageFolder = "uploads/productImage";
        String imageName = UUID.randomUUID()+"_"+image.getOriginalFilename();
        Path path1 = Paths.get(imageFolder,imageName);
        image.transferTo(path1);

        productImage.setUrl("/productImage/"+ imageName);

        productImageRepository.save(productImage);

    }

    public List<ProductImageResponse> getAllImage(Long id){

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(" " +  id + " Product id Not Found"));

        List<ProductImage> imgs =
                productImageRepository.findByProduct(product);

        return imgs.stream()
                .map(productMapper::productImageToDto)
                .toList();
    }

    public void addImages(Long id,
                          List<MultipartFile> images)
            throws IOException {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(" " +  id + " Product id Not Found"));





        Long alreadyImageCount =
                productImageRepository.countByProductId(id);

        if(images == null || images.isEmpty()){

            throw new FileNotFoundException(" file must attach ");
        }

        if(alreadyImageCount + images.size() <= 6){

            for(MultipartFile image : images){

                if(image.isEmpty()){
                    throw new FileNotFoundException(" file upload correctly ");
                }


                String contentType = image.getContentType();
                if(contentType == null || !contentType.startsWith("image/") ){
                    throw new FileFormatWrongException(" upload image format only or upload image ");

                }


                String imageFolder = "uploads/productImage";
                String imageName = UUID.randomUUID()+"_"+image.getOriginalFilename();
                Path path1 = Paths.get(imageFolder,imageName);
                image.transferTo(path1);


                ProductImage productImage =
                        new ProductImage();

                productImage.setProduct(product);
                productImage.setUrl("/productImage/"+imageName);

                productImageRepository.save(productImage);
            }

        }else{

            throw new MaxLimitForImage(
                    "images should be within 6");
        }
    }


}
