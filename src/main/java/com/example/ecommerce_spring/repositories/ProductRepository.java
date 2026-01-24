package com.example.ecommerce_spring.repositories;

import com.example.ecommerce_spring.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}