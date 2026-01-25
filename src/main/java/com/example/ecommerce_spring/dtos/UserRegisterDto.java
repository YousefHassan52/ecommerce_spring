package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRegisterDto {
    private String name;
    private String email;
    private String password;

    public static UserRegisterDto toUserRegisterDto(User user) {
        return new UserRegisterDto(user.getName(), user.getEmail(), user.getPassword());
    }

    public  User toUser() {
        User user = new User();
        user.setName(this.getName());
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        return user;
    }
}
