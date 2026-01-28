package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.Category;
import com.example.ecommerce_spring.entities.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
public class AddProductDto {
    private String name;
    private BigDecimal price;
    private String description;
    @JsonProperty("category_id")
    private Byte categoryId;
    public static AddProductDto productToDto(Product product) {
        return new AddProductDto(
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory().getId()
        );
    }
    public Product toProduct() {
        Product product = new Product();
        product.setName(this.getName());
        product.setPrice(this.getPrice());
        product.setDescription(this.getDescription());
        if(this.categoryId!=null){
            product.setCategory(new Category(categoryId));
        }
        return product;
    }
}
