package com.example.ecommerce_spring.dtos;
import com.example.ecommerce_spring.entities.Order;
import com.example.ecommerce_spring.entities.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseDto {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("session_url")
    private String sessionUrl;

    public static CheckoutResponseDto toDto(Order order,String sessionUrl)
    {
        return new CheckoutResponseDto(
                order.getId(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                sessionUrl




        );
    }
}
