package com.example.productcatalogservice.controllers;


import com.example.productcatalogservice.Services.IProductService;
import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;


    @GetMapping("/products")
    public List<ProductDto> getProducts(){
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
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return null;
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

}
