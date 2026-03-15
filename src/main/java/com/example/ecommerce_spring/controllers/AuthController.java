package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.*;
import com.example.ecommerce_spring.exceptions.UserEmailNotFoundException;
import com.example.ecommerce_spring.repositories.UserRepository;
import com.example.ecommerce_spring.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // 1-
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @Valid @RequestBody UserLoginDto userLoginDto,
            HttpServletResponse response // it didnt send my client it created by tomcat
                                         // every time this method or api called spring will create HttpServletResponse object
    ){

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken( // howa hena hy-check 2za kan el email s7 + 2za kan el password kman s7
                    userLoginDto.getEmail(),
                    userLoginDto.getPassword()
            )
        );
        var user=userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(); // kda kda lw el email m4 mawgod el exception haye7sal mn el lines el fo2

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie =new Cookie("refreshToken",refreshToken);
        cookie.setPath("/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(accessToken));

    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestHeader("Authorization") String token
    )
    {
        token=token.replace("Bearer ", "");
        boolean valid= jwtService.validateToken(token);

        return ResponseEntity.ok(new ValidTokenDto(valid));

    }
    @GetMapping("/current_user")
    ResponseEntity<UserDto> getCurrentUser(){
        var auth= SecurityContextHolder.getContext().getAuthentication();
        Long id= (Long) auth.getPrincipal(); // hena b-get id m4 email 5las
        // howa el subject mb2a4 m5zen email b2a m5zn id
        var user= userRepository.findById(id).orElse(null);
        if (user==null)
        {
            throw new UserEmailNotFoundException();
        }
        UserDto userDto=UserDto.userToDto(user);
        return ResponseEntity.ok(userDto);


    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> emailOrPasswordNotCorrectException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("Email or Password Not Correct"));
    }
}
