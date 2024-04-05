package com.spring.pos2.service;

import com.spring.pos2.dto.CategoryReq;
import com.spring.pos2.entity.Category;
import com.spring.pos2.repository.CategoryRepo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Resource
    private RedisTemplate<String, CategoryReq> redisTemplate;

    public List<CategoryReq> findAll(){
        List<Category> categories = categoryRepo.findAll();
        return categories.stream()
                .map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }

//    public List<CategoryReq> findAll(){
//        List<CategoryReq> categoryReqs = (List<CategoryReq>) redisTemplate.opsForValue().get("AllCategory");
//        if(categoryReqs == null){
//            categoryReqs = categoryRepo.findAll();
//            redisTemplate.opsForValue().set("AllCategory", categoryReqs);
//        }
//        return categoryReqs.stream()
//                .map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()))
//                .collect(Collectors.toList());
//    }


    public Optional<CategoryReq> findById(Long id){
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        return categoryOptional.map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()));
    }

    public CategoryReq create(CategoryReq categoryReq){
        Category category = new Category(categoryReq.getCategoryId(), categoryReq.getCategoryName());
        category = categoryRepo.save(category);
        return new CategoryReq(category.getCategoryId(), category.getCategoryName());
    }

    public CategoryReq createRedis(CategoryReq categoryReq){
        String redisKey = "category:"+ categoryReq.getCategoryId();
        redisTemplate.opsForValue().set(redisKey, categoryReq, 1000);
        return categoryReq;
    }

    public List<CategoryReq> findAllRedis(){
        List<CategoryReq> categories = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("category:*");

        for (String key : keys){
            CategoryReq categoryReq = redisTemplate.opsForValue().get(key);
            categories.add(categoryReq);
        }

        System.out.println("Data Kategori berhasil diambil dari Redis");
        return categories;
    }
}
