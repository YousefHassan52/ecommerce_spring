package com.example.ecommerce_spring.dtos;


import com.example.ecommerce_spring.entities.Order;
import com.example.ecommerce_spring.entities.OrderItem;
import com.example.ecommerce_spring.entities.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@JsonPropertyOrder({"order_id", "customer_id", "order_status", "total_price", "created_at", "items"})

public class OrderDto {

    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("items")
    private List<OrderItemDto> items = new ArrayList<>();
    public OrderDto(Order order){
        this.orderId=order.getId();
        this.customerId=order.getCustomer().getId();
        this.orderStatus=order.getStatus();
        this.totalPrice=order.getTotalPrice();
        this.createdAt=order.getCreatedAt();

        for(OrderItem item:order.getOrderItemList()){
            OrderItemDto itemDto=new OrderItemDto(item);
            this.items.add(itemDto);
        }

    }

}
