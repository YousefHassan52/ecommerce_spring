package com.example.ecommerce_spring.repositories;

import com.example.ecommerce_spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
