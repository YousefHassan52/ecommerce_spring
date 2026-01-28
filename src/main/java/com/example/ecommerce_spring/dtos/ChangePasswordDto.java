package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePasswordDto {
    @JsonProperty("old_password")
    private String oldPassword;
    @JsonProperty("new_password")
    private String newPassword;

    public User toUser() {
        User user = new User();
        user.setPassword(this.newPassword);
        return user;
    }

}
