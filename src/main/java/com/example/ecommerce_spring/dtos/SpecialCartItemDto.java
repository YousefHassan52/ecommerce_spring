package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialCartItemDto {
    @JsonProperty("product_id")
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int quantity;
    @JsonProperty("total_price")
    private double totalPrice;

    public static SpecialCartItemDto toSpecialCartItemDto(CartItem cartItem) {
        return new SpecialCartItemDto(
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getProduct().getDescription(),
                cartItem.getQuantity(),
                cartItem.getQuantity()*cartItem.getProduct().getPrice().doubleValue()
        );
    }
}
