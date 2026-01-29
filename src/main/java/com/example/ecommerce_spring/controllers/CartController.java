package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.*;
import com.example.ecommerce_spring.entities.Cart;
import com.example.ecommerce_spring.entities.CartItem;
import com.example.ecommerce_spring.repositories.CartRepository;
import com.example.ecommerce_spring.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
  private final ProductRepository productRepository;
  private CartRepository cartRepository;

  @PostMapping("")
  public ResponseEntity<SpecialCartDto> addCart(){
    Cart cart=cartRepository.save(new Cart());

    SpecialCartDto cartDto=new SpecialCartDto(cart);
    return ResponseEntity.ok(cartDto);

  }
  @PostMapping("/{id}/add-item")
    public ResponseEntity<SpecialCartItemDto> addItemToCart(
            @PathVariable UUID id,
          @Valid @RequestBody AddItemDto item
  ){

    var cart=cartRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
     var product=productRepository.findById(item.getProductId()).orElse(null);
     if(product==null)
     {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cart_id not found"); // throw 404 but without error msg
     }

    var cartItem = cart.getCartItems().stream()
            .filter(ci -> ci.getProduct().getId().equals(item.getProductId()))
            .findFirst()
            .orElse(null);
    if(cartItem==null) // el item dh 2we mara yedafe
    {
       cartItem=new CartItem();
      cartItem.setCart(cart);
      cartItem.setProduct(product);
      cartItem.setQuantity(1);
      cart.getCartItems().add(cartItem);
      // m4 me7tag assign el item to product

    }
    else
    {
        cartItem.setQuantity(cartItem.getQuantity()+1);
    }

    cartRepository.save(cart);
    return ResponseEntity.ok(SpecialCartItemDto.toSpecialCartItemDto(cartItem));



    // carRepo.save(cart);

  }



    @GetMapping("/{id}")
    public ResponseEntity<SpecialCartDto> getCartById(@PathVariable UUID id){
        var cart=cartRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        SpecialCartDto specialCartDto=new SpecialCartDto(cart);


        // get total price

        return ResponseEntity.ok().body(specialCartDto);
    }

    @PutMapping("/{id}/update-product/{productId}")
    public ResponseEntity<SpecialCartItemDto> updateCartItem(
            @PathVariable UUID id,
            @PathVariable Long productId,
            @RequestBody UpdateCartItemDto body
    ){
      var cart=cartRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        var cartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if(cartItem==null)
        {
            return ResponseEntity.badRequest().build();
        }
        cartItem.setQuantity(body.getQuantity());
        cartRepository.save(cart);

        return ResponseEntity.ok().body(SpecialCartItemDto.toSpecialCartItemDto(cartItem));



    }



}
