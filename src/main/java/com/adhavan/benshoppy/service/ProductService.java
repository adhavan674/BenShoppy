package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.dto.product.*;
import com.adhavan.benshoppy.dto.user.UpdateStatusRequest;
import com.adhavan.benshoppy.entity.*;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.mapper.ProductMapper;
import com.adhavan.benshoppy.repository.CategoryRepository;
import com.adhavan.benshoppy.repository.ProductImageRepository;
import com.adhavan.benshoppy.repository.ProductRepository;
import com.adhavan.benshoppy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Transactional
    public void addProduct(Long seller_id, Long category_id,CreateProductRequest dto) throws IOException {

        User user = userRepository.findById(seller_id)
                .orElseThrow(() -> new ResourceNotFoundException(" User Not Found"));
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + category_id + " Category Not Found"));

        if(dto.getThumbnail()==null || dto.getThumbnail().isEmpty() || dto.getImages()==null || dto.getImages().isEmpty()){
            throw new IllegalArgumentException("Thumbnail and at least one product image is required");
        }
        for (MultipartFile img : dto.getImages()){
            if(img.isEmpty()){
                throw new IllegalArgumentException("Product image Cannot be empty ");
            }
        }

        Product newproduct = productMapper.CreateProductToProduct(dto);
        newproduct.setUser(user);
        newproduct.setCategory(category);
        newproduct.setStatus(Status.ACTIVE);

        String folder = "uploads/thumbnail";
        String filename = UUID.randomUUID() + "_" + dto.getThumbnail().getOriginalFilename();
        Path path = Paths.get(folder, filename);
        dto.getThumbnail().transferTo(path);
        newproduct.setThumbnail("/thumbnail/" + filename);

        Product pro = productRepository.save(newproduct);

        for (MultipartFile image : dto.getImages()) {


            String imageFolder = "uploads/productImage";
            String imageName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path1 = Paths.get(imageFolder, imageName);
            image.transferTo(path1);

            ProductImage img = new ProductImage();
            img.setUrl("/productImage/" + imageName);
            img.setProduct(pro);

            productImageRepository.save(img);

        }

    }
    public List<SummaryProductResponse> getProductByStatus(Long id,Status status) {

        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException( " " + id +  " User id not found "));

     List<Product> products =  productRepository.findByUserAndStatus(user,status);


     return products.stream()
              .map(productMapper::productToSummary).toList();

    }

    public DetailsProductResponse getProductDetails(Long id) {

       Product product = productRepository.findById(id)
                           .orElseThrow(() -> new ResourceNotFoundException(" " +  id + " Product id Not Found"));
       String categoryName = product.getCategory().getName();
       Category category = product.getCategory();
       String user = product.getUser().getName();
       List<ProductImage> images = productImageRepository.findByProduct(product);

       List<ProductImageResponse> imageResponses =  images.stream().map(productMapper::productImageToDto).toList();

       DetailsProductResponse productDetails = productMapper.productToDetails(product);
       productDetails.setProduct_id(product.getId());
       productDetails.setCategory_name(category.getName());
       productDetails.setCategory_id(category.getId());
       productDetails.setImages(imageResponses);
       productDetails.setSeller_name(user);

       return productDetails;

    }

    @Transactional
    public void deleteProduct(Long id) {

       Product product =  productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( " "+ id + " Product Not Found"));

        productImageRepository.deleteByProduct(product);
        productRepository.deleteById(id);

    }

    public void updateProductStatus(Long id, UpdateStatusRequest dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " +  id + " Product Not Found"));


        product.setStatus(dto.getStatus());
        productRepository.save(product);


    }

    public void updateProduct(Long product_id,UpdateProductRequest dto) throws IOException {

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResourceNotFoundException(" " +  product_id + " Product Not Found"));

        if(dto.getName()!=null){
            product.setName(dto.getName());
        }
        if(dto.getPrice()!=null){
            product.setPrice(dto.getPrice());
        }

        if(dto.getDescription()!=null){
            product.setDescription(dto.getDescription());
        }
        if(dto.getStock()!=null){
            product.setStock(dto.getStock());
        }
        if(dto.getCategory_id()!=null){
            Category category = categoryRepository.findById(dto.getCategory_id())
                    .orElseThrow(() -> new ResourceNotFoundException(" " +  dto.getCategory_id() + " Category Not Found"));
            product.setCategory(category);
        }

        if(dto.getThumbnail()!=null && !dto.getThumbnail().isEmpty()){

            String oldFile = product.getThumbnail().substring(10);
            String folder = "uploads/thumbnail";
            Path path= Paths.get(folder,oldFile);
            Files.deleteIfExists(path);


            String filename = UUID.randomUUID() +"_"+ dto.getThumbnail().getOriginalFilename();
            Path paths = Paths.get(folder,filename);
            dto.getThumbnail().transferTo(paths);
            product.setThumbnail("/thumbnail/"+filename);
        }

        productRepository.save(product);
    }


    public List<SummaryProductResponse> searchProduct(Long id,String name) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " +  id + " User Not Found"));
       List<Product> products =  productRepository.findByUserAndNameContaining(user,name);

         return products.stream().map(productMapper::productToSummary).toList();

    }


    public List<SummaryProductResponse> getLatestAdded(Long id) {

            return productRepository.getLatestForSeller(id)
                    .stream().map(productMapper::productToSummary)
                    .toList();

    }

    public List<SummaryProductResponse> getLatestProduct() {
        return productRepository.getLatest()
                .stream().map(productMapper::productToSummary)
                .toList();

    }

    public List<SummaryProductResponse> getProductByCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + id + " Category Not Found"));

        List<Product> products = productRepository.findByCategoryAndStatus(category,Status.ACTIVE);

      return  products.stream().map(productMapper::productToSummary).toList();

    }

    public List<SummaryProductResponse> getByRelatedName(String name,Long category_id,Long product_id) {

        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + category_id + " Category Not Found"));
        String search = name.substring(0,3);
        List<Product> products = productRepository.findRecommendProduct(search.toLowerCase(),product_id);

        if(products.size()<5){

            List<Product> products1 = productRepository.findRecommendProductWithCategory(search.toLowerCase(),category_id,product_id);
          return   products1.stream().map(productMapper::productToSummary).toList();

        } else {
          return   products.stream().map(productMapper::productToSummary).toList();

        }


    }

    public Page<SummaryProductResponse> getProductForUser(String name, int page, int size, String sortBy,String direction) {


        Sort sort = direction.equalsIgnoreCase("asc")
                           ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Product> products  =  productRepository.findByNameContainingIgnoreCaseAndStatus(name,pageable,Status.ACTIVE);

       Page<SummaryProductResponse> responses = products.map(productMapper::productToSummary);

        return responses;
    }

    public List<String> getTextSuggest(String name) {

        return productRepository.textSuggest(name);

    }

    public void updateProductPrice(Long id, UpdateProductPrice dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( " " + id + " Product id not found"));
        product.setPrice(dto.getPrice());
        productRepository.save(product);
    }

    public List<SummaryProductResponse> getProducts(Long id) {
      User user = userRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException(" " + id + " User id not found "));
      List<Product> products = productRepository.findByUser(user);
      return products.stream().map(productMapper::productToSummary).toList();

    }
}
