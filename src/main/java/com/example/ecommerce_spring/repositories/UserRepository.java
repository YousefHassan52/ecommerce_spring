package com.example.ecommerce_spring.repositories;

import com.example.ecommerce_spring.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
