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

    var cartItem = cart.getCartItemByProductId(item.getProductId());
   cartItem= cart.addItemToCart(cartItem,product);
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
    public ResponseEntity<?> updateCartItem(
            @PathVariable UUID id,
            @PathVariable Long productId,
            @RequestBody UpdateCartItemDto body
    ){
      var cart=cartRepository.findById(id).orElse(null);
      if(cart==null)
      {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("cart not found"));
      }

        var cartItem = cart.getCartItemByProductId(productId);

        if(cartItem==null)
        {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("product not found"));
        }
        cartItem.setQuantity(body.getQuantity());
        cartRepository.save(cart);

        return ResponseEntity.ok().body(SpecialCartItemDto.toSpecialCartItemDto(cartItem));



    }

    @DeleteMapping("/{cart_id}/remove-item/{product_id}")
    ResponseEntity<?> removeItemFromCart(
            @PathVariable UUID cart_id,
            @PathVariable Long product_id

    ){
        var cart=cartRepository.findById(cart_id).orElse(null);
        if(cart==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("cart not found"));
        }

        var cartItem = cart.getCartItemByProductId(product_id);


        if(cartItem!=null)
        {
            cart.removeItemFromCart(cartItem);
            cartRepository.save(cart);
            return ResponseEntity.noContent().build();

        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("product not found"));
        }

    }



}
