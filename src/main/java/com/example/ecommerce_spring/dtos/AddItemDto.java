package com.example.ecommerce_spring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddItemDto {

    @NotNull(message = "product_id cannot be null")
    @JsonProperty("product_id")
    private Long productId;
}
