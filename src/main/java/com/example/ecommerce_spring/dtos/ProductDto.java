package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Byte category_id;
    public static ProductDto productToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory().getId()!=null?product.getCategory().getId():null
        );
    }
}
