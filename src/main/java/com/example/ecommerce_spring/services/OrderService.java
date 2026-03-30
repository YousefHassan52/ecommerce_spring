package com.example.ecommerce_spring.services;

import com.example.ecommerce_spring.dtos.OrderDto;
import com.example.ecommerce_spring.entities.Order;
import com.example.ecommerce_spring.entities.OrderStatus;
import com.example.ecommerce_spring.exceptions.CartEmptyException;
import com.example.ecommerce_spring.exceptions.CartNotFoundException;
import com.example.ecommerce_spring.exceptions.OrderAccessDenied;
import com.example.ecommerce_spring.exceptions.OrderNotFoundException;
import com.example.ecommerce_spring.repositories.CartRepository;
import com.example.ecommerce_spring.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public List<OrderDto> getUserOrders(){
        var user=authService.getCurrentUser();

        var orders= orderRepository.findAllByCustomer_Id(user.getId());
        List<OrderDto> orderDtos=new ArrayList<>();
        for(Order order:orders)
        {
            orderDtos.add(new OrderDto(order));


        }
        return orderDtos;



    }
    public List<OrderDto> getAllOrders(){
       List<Order> orders= orderRepository.findAll();
        List<OrderDto> orderDtos=new ArrayList<>();
        for(Order order:orders)
        {
            orderDtos.add(new OrderDto(order));


        }
        return orderDtos;
    }

    public Order getUserOrder(Long orderId){
        var user=authService.getCurrentUser();
        var order=orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if(!Objects.equals(order.getCustomer().getId(), user.getId())){
            throw new OrderAccessDenied();

        }

        return order;

    }

    public Order updateOrderStatus(Long orderId,OrderStatus newStatus){
        var order=orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.setStatus(newStatus);
        return orderRepository.save(order); // return the updated order
    }
}
