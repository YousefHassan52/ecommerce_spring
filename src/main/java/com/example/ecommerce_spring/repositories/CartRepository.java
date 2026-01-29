package com.example.ecommerce_spring.repositories;

import com.example.ecommerce_spring.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}