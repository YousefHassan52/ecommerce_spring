package com.example.ecommerce_spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "Yousef Hassan");
        return "index";
    }
    @RequestMapping("/name")
    public String name() {
        return "say_my_name";
    }
}
