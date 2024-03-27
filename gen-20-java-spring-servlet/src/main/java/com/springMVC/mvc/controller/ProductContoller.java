package com.springMVC.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller//Return berupa halaman html nya
@RequestMapping("")
public class ProductContoller {

    @GetMapping
    public String welcome(Model model){
        String message = "Hello World!";
        model.addAttribute("title",message);
        return "index";
    }
}
