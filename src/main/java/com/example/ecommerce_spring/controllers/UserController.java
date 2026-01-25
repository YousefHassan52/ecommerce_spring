package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.dtos.UserDto;
import com.example.ecommerce_spring.dtos.UserRegisterDto;
import com.example.ecommerce_spring.entities.User;
import com.example.ecommerce_spring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
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
    public UserDto createUser(@RequestBody UserRegisterDto userDto) {

        var user= userRepository.save(userDto.toUser());

        return UserDto.userToDto(user);



        // return in response id name email(userDto)
    }

}
