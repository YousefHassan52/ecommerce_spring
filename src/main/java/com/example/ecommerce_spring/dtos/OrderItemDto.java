package com.example.ecommerce_spring.dtos;

import com.example.ecommerce_spring.entities.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class OrderItemDto {

    @JsonProperty("item_id")
    private Long orderItemId;

    @JsonProperty("product_id")
    private Long ProductId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("unit_price")
    private BigDecimal unitPrice;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    public OrderItemDto(OrderItem orderItem){
        this.orderItemId= orderItem.getId();
        this.ProductId=orderItem.getProduct().getId();
        this.productName=orderItem.getProduct().getName();
        this.quantity= orderItem.getQuantity();
        this.unitPrice=orderItem.getUnit_price();
        this.totalPrice=orderItem.getTotal_price();
    }


}
