package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateUserDto {
    private String name;
    private String email;
    private String password;

    public User toUser(){
       User user= new User();
       user.setName(this.getName());
       user.setEmail(this.getEmail());
       user.setPassword(this.getPassword());
       return user;
    }
}
