package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.*;

import com.example.ecommerce_spring.exceptions.CartNotFoundException;
import com.example.ecommerce_spring.exceptions.ProductNotFoundException;
import com.example.ecommerce_spring.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;


    // creating cart
  @PostMapping("")
  public ResponseEntity<SpecialCartDto> addCart(){
    var cart=cartService.createCart();
    return ResponseEntity.ok(cart);

  }

  // add item to cat
  @PostMapping("/{id}/add-item")
    public ResponseEntity<SpecialCartItemDto> addItemToCart(
            @PathVariable UUID id,
          @Valid @RequestBody AddItemDto item
  ){
    var cart=cartService.addItemToCart(id,item.getProductId());
    return ResponseEntity.ok(cart);
  }

    // get cart by id
    @GetMapping("/{id}")
    public ResponseEntity<SpecialCartDto> getCartById(@PathVariable UUID id){

        var cart=cartService.getCartById(id);
        return ResponseEntity.ok().body(cart);
    }

    @PutMapping("/{id}/update-product/{productId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable UUID id,
            @PathVariable Long productId,
            @RequestBody UpdateCartItemDto body
    ){
      var cart=cartService.updateCartItem(id,productId,body.getQuantity());

        return ResponseEntity.ok().body(cart);



    }

    // remove item from cart
    @DeleteMapping("/{cart_id}/remove-item/{product_id}")
    ResponseEntity<?> removeItemFromCart(
            @PathVariable UUID cart_id,
            @PathVariable Long product_id

    ){
        cartService.deleteItemFromCart(cart_id,product_id);
        return ResponseEntity.noContent().build();


    }

    // clear all cart items
    @DeleteMapping("/{id}")
    public ResponseEntity<?> clearCart(
            @PathVariable UUID id
    )
    {
        cartService.clearCart(id);
        return ResponseEntity.noContent().build();
    }

    // exception handlers
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(){

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("product not found"));
    }



}
