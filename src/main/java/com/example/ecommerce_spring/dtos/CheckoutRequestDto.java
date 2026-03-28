package com.example.ecommerce_spring.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@AllArgsConstructor
@Getter
public class CheckoutRequestDto {
    @JsonProperty( "cart_id")
    @NotNull(message = "cart id is required")
    private UUID cartId;


}

