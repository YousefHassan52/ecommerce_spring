package com.example.ecommerce_spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
    @RequestMapping("/name")
    public String name() {
        return "say_my_name.html";
    }
}
