package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.entities.User;
import com.example.ecommerce_spring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserController {
    private UserRepository userRepository;
    @GetMapping("/users")
    public Iterable<User> users() {
        return userRepository.findAll();
    }
}
