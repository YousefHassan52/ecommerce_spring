package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.*;
import com.example.ecommerce_spring.exceptions.EmailAlreadyExistsException;
import com.example.ecommerce_spring.exceptions.EmailOrPasswordNotCorrectException;
import com.example.ecommerce_spring.exceptions.ProductNotFoundException;
import com.example.ecommerce_spring.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @GetMapping("")
    public Iterable<UserDto> users(
            @RequestParam(name = "sort",required = false,defaultValue = "id") String sort
    ) {
        // names of vars in entities
        if(!Set.of("name","email","id").contains(sort)) {
            sort="id";
        }
        //       return userRepository.findAll().stream().map( user->UserDto.userToDto(user)).toList();
        // equivalent to the below
       return userRepository.findAll(Sort.by(sort)).stream().map(UserDto::userToDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));

        // if there not user with id 44 as example you will not reach this line
        // function will stop at exception line

        return ResponseEntity.ok(UserDto.userToDto(user));
    }
    @PostMapping("")
    public ResponseEntity<?> registerUser(
             @Valid @RequestBody UserRegisterDto userDto) {
        // kda spring fehem talma 2na passer object 2ni hab3tloh values of its variables
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        var user=userDto.toUser();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.userToDto(user));



        // return in response id name email(userDto)
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UpdateUserDto updateUserDto,
            @PathVariable Long id)
    {
        var user = userRepository.findById(id).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!updateUserDto.getName().isEmpty()) {
            user.setName(updateUserDto.getName());
        }
        if(!updateUserDto.getEmail().isEmpty()) {
            user.setEmail(updateUserDto.getEmail());
        }
        if(!updateUserDto.getPassword().isEmpty()) {
            user.setPassword(updateUserDto.getPassword());
        }
        userRepository.save(user);
        return ResponseEntity.ok(UserDto.userToDto(user));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        var user=userRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        userRepository.delete(user);
        return ResponseEntity.noContent().build();

    }
    @PostMapping("/{id}/change_password")
    public ResponseEntity<UserDto> changePassword(
            @RequestBody ChangePasswordDto body,
            @PathVariable Long id
    ){
        var user=userRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!body.getOldPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(body.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }


    // exception handlers
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> emailAlreadyExistsException(){

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto("Email Already Exists"));
    }




}
