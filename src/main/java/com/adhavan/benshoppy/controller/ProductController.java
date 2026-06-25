package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.product.*;
import com.adhavan.benshoppy.dto.user.UpdateStatusRequest;
import com.adhavan.benshoppy.entity.Product;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "Product APIs" , description = " Product management APIs ")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    @Autowired
    private ProductService productService;


    @Operation(summary = "Add Product")
    @PostMapping(value = "/seller/product/{seller_id}/{category_id}" ,
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addProduct(@Valid @ModelAttribute CreateProductRequest dto,
                             @PathVariable Long seller_id,
                             @PathVariable Long category_id ) throws IOException {

        productService.addProduct(seller_id,category_id,dto);

        return "product Added Successfully";

    }
    @Operation(summary = "Update Product")
    @PatchMapping(value = "/seller/product/{product_id}",
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateProduct(@Valid @ModelAttribute UpdateProductRequest dto,
                                @PathVariable long product_id ) throws IOException {

        productService.updateProduct(product_id,dto);

        return "Product updated successfully";
    }

    @Operation(summary = "Update Product Status", description = "this API can access by SELLER AND ADMIN")
    @PreAuthorize("hasAnyRole('ADMIN',SELLER')")
    @PatchMapping("/product/{product_id}/status")
    public String updateProductStatus(@PathVariable Long product_id, @RequestBody UpdateStatusRequest dto){

        productService.updateProductStatus(product_id,dto);

        return dto.getStatus() + " updated successfully ";

    }
    @Operation(summary = "Update price for a Product")
    @PatchMapping("/seller/{product_id}/price")
    public String updateProductPrice(@PathVariable Long product_id,@Valid @RequestBody UpdateProductPrice dto){
           productService.updateProductPrice(product_id,dto);
           return " product price updated ";
    }
    @Operation(summary = "delete Product" ,
            description = "delete product only if no relation")
    @DeleteMapping("/seller/product/{product_id}")
    public String deleteProduct(@PathVariable Long product_id){

        productService.deleteProduct(product_id);

        return "successfully product deleted ";

    }
    @Operation(summary = "get Seller All Products")
    @GetMapping("/seller/{seller_id}/products")
    public List<SummaryProductResponse> getProducts(@PathVariable Long seller_id){
       return productService.getProducts(seller_id);
    }

    @Operation(summary = "latest Products added by seller")
    @GetMapping("seller/{seller_id}/latest")
    public List<SummaryProductResponse> getLatestAddedProduct(@PathVariable Long seller_id){

        return productService.getLatestAdded(seller_id);

    }
    @Operation(summary = "search his own products")
    @GetMapping("/seller/{seller_id}/search")
    public List<SummaryProductResponse> getProductBySearch(@PathVariable Long seller_id,@RequestParam String name){

        return productService.searchProduct(seller_id,name);

    }

    @Operation(summary = "Get Products by status")
    @GetMapping("/seller/{seller_id}")
    public List<SummaryProductResponse> getProductByStatus(@RequestParam Status status,@PathVariable Long seller_id){

      return productService.getProductByStatus(seller_id,status);

    }


    @Operation(summary = "Full details about product")
    @GetMapping("/public/{product_id}/details")
    public DetailsProductResponse getProductDetails(@PathVariable Long product_id){

       return productService.getProductDetails(product_id);

    }









    @Operation(summary = "recently added products")
    @GetMapping("/public/latest") //ok
    public List<SummaryProductResponse> getLatestProducts() {

        return productService.getLatestProduct();
    }

    @Operation(summary = "Get Products by category")
    @GetMapping("/public/category/{category_id}")
    public List<SummaryProductResponse> getProductByCategory(@PathVariable  Long category_id){

        return productService.getProductByCategory(category_id);
    }

   // need debug

    @Operation(summary = "Related product when viewing full details ")
    @GetMapping("/public/related/{product_id}/{category_id}")
    public List<SummaryProductResponse> getRelatedProduct(@RequestParam String name,
                                                          @PathVariable Long product_id ,
                                                          @PathVariable Long category_id){
        
        return productService.getByRelatedName(name,category_id,product_id);

    }

    @Operation(summary = " product search with name ")
    @GetMapping("/public/search")
    public Page<SummaryProductResponse> getProductForUser(@RequestParam String name ,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "4") int size,
                                           @RequestParam(defaultValue = "name") String sortBy,
                                           @RequestParam(defaultValue = "asc") String direction){

        return productService.getProductForUser(name,page,size,sortBy,direction);


    }

    @Operation(summary = "product name suggestion in search box ")
    @GetMapping("/public/textsuggest") //ok
    public List<String> getTextSuggest(@RequestParam String name){

        return productService.getTextSuggest(name);

    }



}
