package com.example.ecommerce_spring.controllers;

import com.example.ecommerce_spring.entities.Msg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {

    @RequestMapping("/json")
    public Msg json() {
        return new Msg("this is first json response");
    }

}
