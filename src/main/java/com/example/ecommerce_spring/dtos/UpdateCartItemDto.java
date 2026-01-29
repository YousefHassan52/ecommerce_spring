package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemDto {


    private int quantity;
    public UpdateCartItemDto(CartItem cartItem) {
        this.quantity = cartItem.getQuantity();
    }

}
