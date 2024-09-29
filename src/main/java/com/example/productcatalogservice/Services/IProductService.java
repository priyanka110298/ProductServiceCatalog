package com.example.productcatalogservice.Services;

import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getProducts();

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product replaceProduct(Long id, Product product);
}
