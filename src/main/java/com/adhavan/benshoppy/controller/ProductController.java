package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.product.*;
import com.adhavan.benshoppy.dto.user.UpdateStatusRequest;
import com.adhavan.benshoppy.entity.Product;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/seller/product") // ok
    public String addProduct(@Valid @ModelAttribute CreateProductRequest dto,
                             @RequestParam MultipartFile thumbnail ,
                             @RequestParam(required = false) List<MultipartFile> images) throws IOException {


        productService.addProduct(dto,thumbnail,images);

        return "product Added Successfully";
    }

    @PatchMapping("/seller/product/{id}")  // ok
    public String updateProduct(@Valid @ModelAttribute UpdateProductRequest dto,
                                @PathVariable long id,
                                @RequestParam(required = false) MultipartFile thumbnail) throws IOException {

        productService.updateProduct(dto, thumbnail, id);

        return "Product updated successfully";
    }

    // seller and admin

    @PreAuthorize("hasAnyRole('ADMIN',SELLER')")
    @PatchMapping("/product/{id}/status") // ok
    public String updateProductStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest dto){

        productService.updateProductStatus(id,dto);

        return dto.getStatus() + " updated successfully ";

    }

    @PatchMapping("/seller/{id}/price") // ok
    public String updateProductPrice(@PathVariable Long id,@Valid @RequestBody UpdateProductPrice dto){
           productService.updateProductPrice(id,dto);
           return " product price updated ";
    }

    @DeleteMapping("/seller/product/{id}")
    public String deleteProduct(@PathVariable Long id){

        productService.deleteProduct(id);

        return "successfully product deleted ";

    }

    @GetMapping("/seller/{id}/products") //ok
    public List<SummaryProductResponse> getProducts(@PathVariable Long id){
       return productService.getProducts(id);
    }

    @GetMapping("seller/{id}/latest") // ok
    public List<SummaryProductResponse> getLatestAddedProduct(@PathVariable Long id){

        return productService.getLatestAdded(id);

    }

    @GetMapping("/seller/{id}/search")
    public List<SummaryProductResponse> getProductBySearch(@PathVariable Long id,@RequestParam String name){

        return productService.searchProduct(id,name);

    }

    @GetMapping("/seller/{id}") // ok
    public List<SummaryProductResponse> getProductByStatus(@RequestParam Status status,@PathVariable Long id){

      return productService.getProductByStatus(id,status);

    }


    @GetMapping("/public/{id}/details") // ok
    public DetailsProductResponse getProductDetails(@PathVariable Long id){

       return productService.getProductDetails(id);

    }










    @GetMapping("/public/latest") //ok
    public List<SummaryProductResponse> getLatestProducts() {

        return productService.getLatestProduct();
    }

    @GetMapping("/public/category/{id}") //ok
    public List<SummaryProductResponse> getProductByCategory(@PathVariable  Long id){

        return productService.getProductByCategory(id);
    }

    @GetMapping("/public/related") // ok
    public List<SummaryProductResponse> getRelatedProduct(@RequestParam String name,@RequestParam Long id){
        
        return productService.getByRelatedName(name,id);

    }

    @GetMapping("/public/search") // ok
    public Page<SummaryProductResponse> getProductForUser(@RequestParam String name ,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "4") int size,
                                           @RequestParam(defaultValue = "name") String sortBy,
                                           @RequestParam(defaultValue = "asc") String direction){

        return productService.getProductForUser(name,page,size,sortBy,direction);


    }

    @GetMapping("/public/textsuggest") //ok
    public List<String> getTextSuggest(@RequestParam String name){

        return productService.getTextSuggest(name);

    }



}
