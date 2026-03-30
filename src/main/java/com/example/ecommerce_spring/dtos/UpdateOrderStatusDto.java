package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateOrderStatusDto {
    private OrderStatus status;
}
