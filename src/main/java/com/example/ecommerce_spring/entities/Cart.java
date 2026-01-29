package com.example.ecommerce_spring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;



    @Column(name = "date_created",insertable = false,updatable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private List<CartItem> cartItems=new ArrayList<>();

    public CartItem getCartItemByProductId(Long productId){
        return this.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItemToCart(CartItem cartItem, Product product){
        if(cartItem==null) // el item dh 2we mara yedafe
        {
            cartItem=new CartItem();
            cartItem.setCart(this);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            this.cartItems.add(cartItem);
            // m4 me7tag assign el item to product

        }
        else
        {
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }
        return cartItem; // 34an lw how can be null y3ni 2wel mara mkan4 fe el cart
                        // te update el cart item el mawgoda fe el controller
    }

    public void removeItemFromCart(CartItem cartItem){
        this.cartItems.remove(cartItem);
    }


}
