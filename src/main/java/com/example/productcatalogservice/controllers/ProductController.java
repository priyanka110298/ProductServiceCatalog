package com.example.productcatalogservice.controllers;


import com.example.productcatalogservice.Services.IProductService;
import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;


    @GetMapping("/products")
    public List<ProductDto> getProducts(){
        List<Product> products = productService.getProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        if(products != null && !products.isEmpty()){
            for(Product product : products) {
                ProductDto productDto = fromProduct(product);
                productDtos.add(productDto);
            }
            return productDtos;
        }
        return null;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){

        try {
            if (productId < 1 || productId > 20) {
                throw new RuntimeException("Invalid product id");
            }

            Product product = productService.getProductById(productId);
            if (product == null) {
                return null;
            }
            return new ResponseEntity<>(fromProduct(product), HttpStatus.OK);
        }catch(RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.createProduct(fromProductDto(productDto));
        return fromProduct(product);
    }

    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        Product product = productService.replaceProduct(id,fromProductDto(productDto));
        return fromProduct(product);
    }

    private ProductDto fromProduct (Product product){
            ProductDto productdto = new ProductDto();
            productdto.setId(product.getId());
            productdto.setTitle(product.getTitle());
            productdto.setDescription(product.getDescription());
            productdto.setAmount(product.getAmount());
            productdto.setImageURL(product.getImageURL());
           if(product.getCategory() != null){
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId(product.getCategory().getId());
                categoryDto.setName(product.getCategory().getName());
                categoryDto.setDescription(product.getCategory().getDescription());
                productdto.setCategory(categoryDto);

           }
            return productdto;
    }

    private Product fromProductDto (ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setAmount(productDto.getAmount());
        product.setImageURL(productDto.getImageURL());
        if(productDto.getCategory() != null){
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);

        }
        return product;
    }

}
