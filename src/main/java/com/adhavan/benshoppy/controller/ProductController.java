package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.product.*;
import com.adhavan.benshoppy.dto.user.UpdateStatusRequest;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // all products
    @GetMapping("/public/products")
    public Page<SummaryProductResponse> getProducts(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "4") int size){
        return productService.getProducts(page,size);
    }

    @PostMapping(value = "/seller/product/{seller_id}/{category_id}" ,
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addProduct(@Valid @ModelAttribute CreateProductRequest dto,
                             @PathVariable Long seller_id,
                             @PathVariable Long category_id ) throws IOException {

        productService.addProduct(seller_id,category_id,dto);

        return "product Added Successfully";

    }

    @PatchMapping(value = "/seller/product/{product_id}",
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateProduct(@Valid @ModelAttribute UpdateProductRequest dto,
                                @PathVariable long product_id ) throws IOException {

        productService.updateProduct(product_id,dto);

        return "Product updated successfully";
    }


    @PatchMapping("/product/{product_id}/status")
    public String updateProductStatus(@PathVariable Long product_id, @RequestBody UpdateStatusRequest dto){

        productService.updateProductStatus(product_id,dto);

        return dto.getStatus() + " updated successfully ";

    }

    @PatchMapping("/seller/{product_id}/price")
    public String updateProductPrice(@PathVariable Long product_id,@Valid @RequestBody UpdateProductPrice dto){
           productService.updateProductPrice(product_id,dto);
           return " product price updated ";
    }

    @DeleteMapping("/seller/product/{product_id}")
    public String deleteProduct(@PathVariable Long product_id) throws IOException {

        productService.deleteProduct(product_id);

        return "successfully product deleted ";

    }
    // seller view his product
    @GetMapping("/seller/{seller_id}/products")
    public Page<SummaryProductResponse> getProductsOfSeller(@PathVariable Long seller_id,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "4") int size){
       return productService.getProductsForSeller(seller_id,page,size);
    }
    // admin view seller product
    @GetMapping("/admin/{seller_id}/products")
    public Page<SummaryProductResponse> getProductsOfSellerAdmin(@PathVariable Long seller_id,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "4") int size){
        return productService.getProductsForSeller(seller_id,page,size);
    }


    @GetMapping("seller/{seller_id}/latest")
    public List<SummaryProductResponse> getLatestAddedProduct(@PathVariable Long seller_id){

        return productService.getLatestAdded(seller_id);

    }

    @GetMapping("/seller/{seller_id}/search")
    public List<SummaryProductResponse> getProductBySearch(@PathVariable Long seller_id,@RequestParam String name){

        return productService.searchProduct(seller_id,name);

    }


    @GetMapping("/seller/{seller_id}")
    public List<SummaryProductResponse> getProductByStatus(@RequestParam Status status,@PathVariable Long seller_id){

      return productService.getProductByStatus(seller_id,status);

    }



    @GetMapping("/public/{product_id}/details")
    public DetailsProductResponse getProductDetails(@PathVariable Long product_id){

       return productService.getProductDetails(product_id);

    }










    @GetMapping("/public/latest")
    public List<SummaryProductResponse> getLatestProducts() {

        return productService.getLatestProduct();
    }


    @GetMapping("/public/category/{category_id}")
    public List<SummaryProductResponse> getProductByCategory(@PathVariable  Long category_id){

        return productService.getProductByCategory(category_id);
    }



    @GetMapping("/public/related/{product_id}/{category_id}")
    public List<SummaryProductResponse> getRelatedProduct(@RequestParam String name,
                                                          @PathVariable Long product_id ,
                                                          @PathVariable Long category_id){
        
        return productService.getByRelatedName(name,category_id,product_id);

    }


    @GetMapping("/public/search")
    public Page<SummaryProductResponse> getProductForUser(@RequestParam String name ,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "4") int size,
                                           @RequestParam(defaultValue = "name") String sortBy,
                                           @RequestParam(defaultValue = "asc") String direction){

        return productService.getProductForUser(name,page,size,sortBy,direction);


    }

    @GetMapping("/public/textsuggest")
    public List<String> getTextSuggest(@RequestParam String name){

        return productService.getTextSuggest(name);

    }



}
