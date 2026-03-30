package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.*;
import com.example.ecommerce_spring.entities.Order;
import com.example.ecommerce_spring.entities.OrderStatus;
import com.example.ecommerce_spring.exceptions.CartEmptyException;
import com.example.ecommerce_spring.exceptions.OrderAccessDenied;
import com.example.ecommerce_spring.exceptions.OrderNotFoundException;
import com.example.ecommerce_spring.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDto> checkout(
            @RequestBody CheckoutRequestDto request
    ){
        var order=orderService.checkout(request.getCartId());
        CheckoutResponseDto responseDto=CheckoutResponseDto.toDto(order);


        return ResponseEntity.ok().body(responseDto);


    }


    @GetMapping("/{orderId}")
        public ResponseEntity<OrderDto> getOrder(
                @PathVariable Long orderId
    ){
        var order=orderService.getUserOrder(orderId);
        return ResponseEntity.ok().body(new OrderDto(order));

    }

    @GetMapping("/me")
    public ResponseEntity<List<OrderDto>> getAllUserOrders(){
        return ResponseEntity.ok().body(orderService.getUserOrders());
    }

    @GetMapping("/admin/get_orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusDto body
    ){
        Order order= orderService.updateOrderStatus(id,body.getStatus());
        return ResponseEntity.ok().body(new OrderDto(order));

    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<ErrorDto> handleCartEmptyException(){

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(new ErrorDto("Empty cart. products may be have been deleted from the platform"));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("order not found"));
    }
    @ExceptionHandler(OrderAccessDenied.class)
    public ResponseEntity<ErrorDto> handle(){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("you are not allowed to access order that you don't own"));
    }


}
