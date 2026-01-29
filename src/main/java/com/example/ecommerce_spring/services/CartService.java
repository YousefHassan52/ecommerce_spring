package com.example.ecommerce_spring.services;

import com.example.ecommerce_spring.dtos.ErrorDto;
import com.example.ecommerce_spring.dtos.SpecialCartDto;
import com.example.ecommerce_spring.dtos.SpecialCartItemDto;
import com.example.ecommerce_spring.entities.Cart;
import com.example.ecommerce_spring.exceptions.CartNotFoundException;
import com.example.ecommerce_spring.exceptions.ProductNotFoundException;
import com.example.ecommerce_spring.repositories.CartRepository;
import com.example.ecommerce_spring.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public SpecialCartDto createCart(){
    Cart cart=cartRepository.save(new Cart());

    SpecialCartDto cartDto=new SpecialCartDto(cart);
    return cartDto;
}

public SpecialCartItemDto addItemToCart(UUID id, Long productId){
    var cart=cartRepository.findById(id).orElse(null);
    if(cart==null)
    {
        throw new CartNotFoundException();
    }
    var product=productRepository.findById(productId).orElse(null);
    if(product==null)
    {
        throw new ProductNotFoundException();
    }

    var cartItem = cart.getCartItemByProductId(productId);
    cartItem= cart.addItemToCart(cartItem,product);
    cartRepository.save(cart);
    return SpecialCartItemDto.toSpecialCartItemDto(cartItem);
}

public SpecialCartDto getCartById(UUID id)
{
    var cart=cartRepository.findById(id).orElse(null);
    if(cart==null)
    {
        throw new CartNotFoundException();
    }

    return new SpecialCartDto(cart);


}

// update cart item
    public SpecialCartItemDto updateCartItem(UUID id ,Long productId,int quantity){
        var cart=cartRepository.findById(id).orElse(null);
        if(cart==null)
        {
            throw new CartNotFoundException();
        }

        var cartItem = cart.getCartItemByProductId(productId);

        if(cartItem==null)
        {
            throw new ProductNotFoundException();

        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return SpecialCartItemDto.toSpecialCartItemDto(cartItem);

    }

    public void deleteItemFromCart(UUID id, Long productId){
        var cart=cartRepository.findById(id).orElse(null);
        if(cart==null)
        {
            throw  new CartNotFoundException();
        }

        var cartItem = cart.getCartItemByProductId(productId);


        if(cartItem==null)
        {
            throw new ProductNotFoundException();

        }
        else{
            cart.removeItemFromCart(cartItem);
            cartRepository.save(cart);
        }
    }

    public void clearCart(UUID id){
        var cart=cartRepository.findById(id).orElse(null);
        if(cart==null)
        {
            throw new CartNotFoundException();
        }
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

}
