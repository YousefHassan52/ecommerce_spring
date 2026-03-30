package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.CheckoutRequestDto;
import com.example.ecommerce_spring.dtos.CheckoutResponseDto;
import com.example.ecommerce_spring.dtos.ErrorDto;
import com.example.ecommerce_spring.exceptions.CartEmptyException;
import com.example.ecommerce_spring.services.OrderService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<CheckoutResponseDto> checkout(
            @RequestBody CheckoutRequestDto request
    ){
        var order=orderService.checkout(request.getCartId());
        CheckoutResponseDto responseDto=CheckoutResponseDto.toDto(order);


        return ResponseEntity.ok().body(responseDto);


    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<ErrorDto> handleCartEmptyException(){

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(new ErrorDto("Empty cart. products may be have been deleted from the platform"));
    }


}
