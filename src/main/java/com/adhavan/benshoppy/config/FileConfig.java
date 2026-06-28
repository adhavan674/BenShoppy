package com.adhavan.benshoppy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/categoryImage/**")
                .addResourceLocations("file:images/categoryImage");

        registry.addResourceHandler("/productImage/**")
                .addResourceLocations("file:images/productImage");

        registry.addResourceHandler("/thumbnail/**")
                .addResourceLocations("file:images/thumbnail");
    }
}
