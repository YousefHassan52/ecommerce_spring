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

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private List<CartItem> cartItems=new ArrayList<>();


}
