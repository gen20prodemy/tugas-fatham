package com.project.project.controller;

import com.project.project.dto.CategoryReq;
import com.project.project.responHandler.CustomResponse;
import com.project.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pos/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public List<CategoryReq> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addCategory(@RequestBody CategoryReq categoryReq){
        CustomResponse response;
        response = categoryService.createCategory(categoryReq);
        return ResponseEntity.ok(response);
    }
}
