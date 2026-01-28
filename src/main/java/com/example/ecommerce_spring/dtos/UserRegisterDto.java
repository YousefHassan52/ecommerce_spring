package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.User;
import com.example.ecommerce_spring.validations.LowerCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRegisterDto {
    @NotBlank(message = "name is required")
    @Size(min = 3,max = 25,message = "name must be between  to 25 charecters")
    @LowerCase
    private String name;

    @Email(message = "Not valid email address")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6,max = 40,message = "password must be between 6 and 40 charecters")
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
