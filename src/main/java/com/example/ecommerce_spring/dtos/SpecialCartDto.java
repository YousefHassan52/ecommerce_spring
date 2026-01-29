package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.Cart;
import com.example.ecommerce_spring.entities.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Setter
public class SpecialCartDto {
    private UUID id;

    @JsonProperty("total_price")
    private BigDecimal totalPrice=BigDecimal.ZERO;

    private ArrayList<SpecialCartItemDto> items = new ArrayList<>();


    public  SpecialCartDto (Cart cart) {
        this.id=cart.getId();
        for(CartItem item: cart.getCartItems())
        {

            var specialItemDto=SpecialCartItemDto.toSpecialCartItemDto(item);
            items.add(specialItemDto);


        }
        for (SpecialCartItemDto specialItemDto: items) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(specialItemDto.getTotalPrice()));
        }

    }
}
