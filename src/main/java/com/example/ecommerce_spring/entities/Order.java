package com.example.ecommerce_spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<OrderItem> orderItemList=new ArrayList<>();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdAt;

    public static Order createOrderFormCart(Cart cart,User customer){
        Order order=new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        cart.getCartItems().forEach(cartItem->{
            OrderItem orderItem=new OrderItem(cartItem,order);// cartItem,order

                  order.getOrderItemList().add(orderItem); // add order item to orderItems list
        });

        BigDecimal totalOrderPrice=BigDecimal.ZERO;
        for (OrderItem orderItem:order.getOrderItemList())
        {
            totalOrderPrice= totalOrderPrice.add(orderItem.getTotal_price()) ;

        }

        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(totalOrderPrice);
        return order;

    }

}
