package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    final UserRepository userRepository;
    @GetMapping ("/hello")
    public String helloAdmin(){
        var auth= SecurityContextHolder.getContext().getAuthentication();
        Long id= (Long) auth.getPrincipal();
        var user=userRepository.findById(id).orElseThrow();
        String res="hello"+ user.getName();
        return res;
    }

}
