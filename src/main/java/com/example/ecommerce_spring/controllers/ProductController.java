package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.ProductDto;
import com.example.ecommerce_spring.entities.Product;
import com.example.ecommerce_spring.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private  ProductRepository productRepository;
@GetMapping("")
    public Iterable<ProductDto> getProducts(
           @RequestParam(name = "sort",required = false) String sort
){
    if(sort == null){
        return productRepository.findAll().stream().map(ProductDto::productToDto).toList();

    } else {
        LinkedHashMap<String,String> sortMap = new LinkedHashMap<>();
        sortMap.put("name","name");
        sortMap.put("email","email");
        sortMap.put("price","price");
        sortMap.put("category_id","category.id");
        String result = sortMap.containsKey(sort) ? sortMap.get(sort) : "id";
        System.out.println(result);
        return productRepository.findAll(Sort.by(result)).stream().map(ProductDto::productToDto).toList();

    }



}

}
