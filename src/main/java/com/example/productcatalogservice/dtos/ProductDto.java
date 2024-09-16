package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.BaseModel;
import com.example.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends BaseModel {
    private Long id;
    private String title;
    private String description;
    private String imageURL;
    private Double amount;
    private CategoryDto category;
}
