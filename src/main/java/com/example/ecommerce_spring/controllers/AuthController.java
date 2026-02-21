package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.ErrorDto;
import com.example.ecommerce_spring.dtos.UserLoginDto;
import com.example.ecommerce_spring.exceptions.EmailOrPasswordNotCorrectException;
import com.example.ecommerce_spring.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthenticationManager authenticationManager;

    // 1-
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @Valid @RequestBody UserLoginDto userLoginDto
    ){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    userLoginDto.getEmail(),
                    userLoginDto.getPassword()
            )
        );

        return ResponseEntity.noContent().build();


    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> emailOrPasswordNotCorrectException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("Email or Password Not Correct"));
    }
}
