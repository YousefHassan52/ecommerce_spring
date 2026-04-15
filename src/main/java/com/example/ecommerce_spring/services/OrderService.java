package com.example.ecommerce_spring.services;

import com.example.ecommerce_spring.dtos.CheckoutResponseDto;
import com.example.ecommerce_spring.dtos.OrderDto;
import com.example.ecommerce_spring.entities.Order;
import com.example.ecommerce_spring.entities.OrderStatus;
import com.example.ecommerce_spring.exceptions.CartEmptyException;
import com.example.ecommerce_spring.exceptions.CartNotFoundException;
import com.example.ecommerce_spring.exceptions.OrderAccessDenied;
import com.example.ecommerce_spring.exceptions.OrderNotFoundException;
import com.example.ecommerce_spring.repositories.CartRepository;
import com.example.ecommerce_spring.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Value("${website.url}")
    private String websiteUrl;

    @Transactional
    public CheckoutResponseDto checkout(UUID cartId) throws StripeException {
        var user = authService.getCurrentUser();

        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        if (cart.getCartItems().isEmpty()) {
            throw new CartEmptyException();
        }

        Order order = Order.createOrderFormCart(cart, user);
        orderRepository.save(order);

        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel");

            order.getOrderItemList().forEach(item -> {
                var lineItem = SessionCreateParams.LineItem.builder()
                        .setQuantity(Long.valueOf(item.getQuantity()))
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmountDecimal(item.getUnit_price().multiply(BigDecimal.valueOf(100)))
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getProduct().getName())
                                                        .build()
                                        ).build()
                        ).build();

                builder.addLineItem(lineItem); // ✅ THE FIX
            });

            var session = Session.create(builder.build());
            cart.getCartItems().clear();
            cartRepository.save(cart);

            return CheckoutResponseDto.toDto(order, session.getUrl());

        } catch (StripeException e) {
            System.out.println(e.getMessage());
            orderRepository.delete(order);
            throw e;
        }
    }

    public List<OrderDto> getUserOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.findAllByCustomer_Id(user.getId());
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(new OrderDto(order));
        }
        return orderDtos;
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(new OrderDto(order));
        }
        return orderDtos;
    }

    public Order getUserOrder(Long orderId) {
        var user = authService.getCurrentUser();
        var order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!Objects.equals(order.getCustomer().getId(), user.getId())) {
            throw new OrderAccessDenied();
        }

        return order;
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}