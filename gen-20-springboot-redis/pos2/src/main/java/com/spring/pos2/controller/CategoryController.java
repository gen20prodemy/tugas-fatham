package com.spring.pos2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.pos2.dto.CategoryReq;
import com.spring.pos2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryReq> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<CategoryReq> findById(@PathVariable("id") Long id){
        return categoryService.findById(id);
    }

    @PostMapping("/add")
    public CategoryReq addCategory(@RequestBody CategoryReq categoryReq){
        return categoryService.create(categoryReq);
    }

    @GetMapping("/all/redis")
    public List<CategoryReq> getAllCategoriesRedis() throws JsonProcessingException {
        return categoryService.findAllRedis();
    }

    @PostMapping("/add/redis")
    public CategoryReq addByRedis(@RequestBody CategoryReq categoryReq){
        return categoryService.createRedis(categoryReq);
    }

}
