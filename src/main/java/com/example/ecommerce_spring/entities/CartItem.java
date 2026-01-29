package com.example.ecommerce_spring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @Column(name="quantity")
    private int quantity;


    // product have many cart items
    // cart item have one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    // cart have many cart items
    // cart item have one cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
