package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.AddProductDto;
import com.example.ecommerce_spring.dtos.ProductDto;
import com.example.ecommerce_spring.entities.Category;
import com.example.ecommerce_spring.entities.Product;
import com.example.ecommerce_spring.repositories.CategoryRepository;
import com.example.ecommerce_spring.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
@Tag(name="Products")
public class ProductController {
    private  ProductRepository productRepository;
    private  CategoryRepository categoryRepository;
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

@GetMapping("/{id}")
public ResponseEntity<ProductDto> getProductById(
        @Parameter(description = "The id of the product to be retrieved")
        @PathVariable Long id){
    var product=productRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    ProductDto productDto=ProductDto.productToDto(product);
    return ResponseEntity.ok().body(productDto);

}

@PostMapping("")
@Operation(summary = "Create a new product")
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody AddProductDto body){

    if(body.getCategoryId() == null){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST," id is null");
    }
    categoryRepository.findById(body.getCategoryId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"there is no category with this id"));

    Product product=productRepository.save(body.toProduct());


    // 2na fe el 25er 3ayez 2_pass lel response body dto 3la mazagy (feh el id)
    return ResponseEntity.status(HttpStatus.CREATED).body(ProductDto.productToDto(product));



}

@DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id){
    var  product=productRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    productRepository.delete(product);
    return ResponseEntity.noContent().build();
}

@PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody AddProductDto body){
    Product product=productRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

    if(!body.getName().isEmpty()){
        product.setName(body.getName());
    }
    if(!body.getDescription().isEmpty()){
        product.setDescription(body.getDescription());
    }
    if(body.getPrice()!=null){
        product.setPrice(body.getPrice());
    }

    if(body.getCategoryId()!=null){
        categoryRepository.findById(body.getCategoryId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        product.setCategory(new Category(body.getCategoryId()));
    }

    productRepository.save(product);
    return ResponseEntity.ok().body(ProductDto.productToDto(product));




}

}
