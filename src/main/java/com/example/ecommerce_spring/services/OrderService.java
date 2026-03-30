package com.example.ecommerce_spring.services;

import com.example.ecommerce_spring.entities.Order;
import com.example.ecommerce_spring.entities.OrderItem;
import com.example.ecommerce_spring.entities.OrderStatus;
import com.example.ecommerce_spring.exceptions.CartEmptyException;
import com.example.ecommerce_spring.exceptions.CartNotFoundException;
import com.example.ecommerce_spring.repositories.CartRepository;
import com.example.ecommerce_spring.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor

@Service
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    @Transactional
    public Order checkout(UUID cartId){
        var user=authService.getCurrentUser();

        var cart=cartRepository.findById(cartId).orElse(null);
        if (cart==null)
        {
            throw new CartNotFoundException();
        }
        if(cart.getCartItems().isEmpty())
        {
            throw new CartEmptyException();
        }

        Order order=Order.createOrderFormCart(cart,user);
        orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);
        return order;



    }
}
