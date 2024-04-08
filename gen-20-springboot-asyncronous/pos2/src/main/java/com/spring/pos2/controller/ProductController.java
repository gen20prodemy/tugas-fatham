package com.spring.pos2.controller;

import com.spring.pos2.dto.ProductReq;
import com.spring.pos2.repository.ProductRepo;
import com.spring.pos2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductReq> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ProductReq> findById(@PathVariable("id") Long id){
        return productService.findById(id);
    }

    @PostMapping("/add")
    public ProductReq create(@RequestBody ProductReq productReq){
        return productService.create(productReq);
    }
}
