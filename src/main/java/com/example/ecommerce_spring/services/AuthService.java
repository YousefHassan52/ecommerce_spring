package com.example.ecommerce_spring.services;

import com.example.ecommerce_spring.entities.User;
import com.example.ecommerce_spring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    public User getCurrentUser(){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Long id=(Long)authentication.getPrincipal();
        return userRepository.findById(id).orElse(null);

    }

}
