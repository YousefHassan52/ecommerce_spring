package com.example.ecommerce_spring.repositories;

import com.example.ecommerce_spring.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}