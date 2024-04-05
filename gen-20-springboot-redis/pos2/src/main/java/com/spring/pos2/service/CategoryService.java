package com.spring.pos2.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private CategoryRepo categoryRepo;
    private RedisTemplate redisTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo, RedisTemplate redisTemplate, ObjectMapper objectMapper){
        this.categoryRepo = categoryRepo;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public List<CategoryReq> findAll(){
        List<Category> categories = categoryRepo.findAll();
        return categories.stream()
                .map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }

    public Optional<CategoryReq> findById(Long id){
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        return categoryOptional.map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()));
    }

    public CategoryReq create(CategoryReq categoryReq){
        Category category = new Category(categoryReq.getCategoryId(), categoryReq.getCategoryName());
        category = categoryRepo.save(category);//Simpan ke database
        String redisKey = "category:"+ category.getCategoryId();
        redisTemplate.opsForValue().set(redisKey, category);//Simpan ke redis
        return new CategoryReq(category.getCategoryId(), category.getCategoryName());
    }

    public CategoryReq createRedis(CategoryReq categoryReq){
        String redisKey = "category:"+ categoryReq.getCategoryId();
        redisTemplate.opsForValue().set(redisKey, categoryReq);
        return categoryReq;
    }

    public List<CategoryReq> getAllCategories() {
        List<Category> categories = categoryRepo.findAll(); // Mendapatkan semua data dari repository

        // Konversi List<Category> menjadi List<CategoryReq>
        List<CategoryReq> categoryReqs = categories.stream()
                .map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()))
                .collect(Collectors.toList());

        return categoryReqs;
    }

    public List<CategoryReq> findAllRedis() throws JsonProcessingException {
        List<CategoryReq> categories;
        List<String> categoriesJson = redisTemplate.opsForList().range("myKey", 0, -1);

        if(categoriesJson.isEmpty()){
            // Ambil data dari database jika Redis kosong
            categories = getAllCategories();
            // Konversi List<CategoryReq> menjadi List<String> JSON
            List<String> categoriesJsonList = categories.stream()
                    .map(categoryReq -> {
                        try {
                            return objectMapper.writeValueAsString(categoryReq);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("Gagal mengonversi CategoryReq ke JSON: " + e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());

            // Simpan data ke Redis dalam bentuk JSON
            redisTemplate.opsForList().rightPushAll("myKey", categoriesJsonList);

        }else{
            categories = categoriesJson.stream()
                    .map(json->{
                            try{
                                return objectMapper.readValue(json, CategoryReq.class);
                            }catch (JsonProcessingException e){
                                throw new RuntimeException("Gagal mengkonversi JSON ke CategoryReq" + e.getMessage());
                            }

                    })
                    .collect(Collectors.toList());
        }

        System.out.println("Data Kategori berhasil diambil dari Redis");
        return categories;
    }

    public void updateRedisData() throws JsonProcessingException {
        // Ambil data dari database
        List<Category> categories = categoryRepo.findAll();

        // Konversi List<Category> menjadi List<CategoryReq>
        List<CategoryReq> categoryReqs = categories.stream()
                .map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()))
                .collect(Collectors.toList());

        // Konversi List<CategoryReq> menjadi List<String> JSON
        List<String> categoriesJson = categoryReqs.stream()
                .map(categoryReq -> {
                    try {
                        return objectMapper.writeValueAsString(categoryReq);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Gagal mengonversi CategoryReq ke JSON: " + e.getMessage());
                    }
                })
                .collect(Collectors.toList());

        // Hapus data lama dari Redis
        redisTemplate.delete("myKey");

        // Simpan data baru ke dalam Redis
        redisTemplate.opsForList().rightPushAll("myKey", categoriesJson);

        System.out.println("Data Kategori di Redis berhasil diperbarui");
    }
}
