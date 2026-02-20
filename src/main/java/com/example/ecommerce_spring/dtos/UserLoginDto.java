package com.example.ecommerce_spring.dtos;


import com.example.ecommerce_spring.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginDto {

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    public User toUser(){
        User user =new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }

}
