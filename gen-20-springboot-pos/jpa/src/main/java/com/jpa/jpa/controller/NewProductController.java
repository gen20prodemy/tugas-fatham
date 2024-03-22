package com.jpa.jpa.controller;

import com.jpa.jpa.entity.NewProduct;
import com.jpa.jpa.service.NewProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/newproduct")
public class NewProductController {

    @Autowired
    private NewProductService newProductService;

    @GetMapping("/all/native")
    public List<NewProduct> getAllNewProducts(){
        return newProductService.getAllNewProducts();
    }

    @GetMapping("/{id}")
    public Optional<NewProduct> getNewProductById(@PathVariable("id") int id){
        return newProductService.getNewProductById(id);
    }

    @GetMapping("/all/jpql")
    public List<NewProduct> getAllUsingJPA(){
        return newProductService.getAllUsingJPA();
    }
}
