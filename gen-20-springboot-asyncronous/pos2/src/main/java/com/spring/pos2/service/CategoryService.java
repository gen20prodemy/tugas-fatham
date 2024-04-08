package com.spring.pos2.service;

import com.spring.pos2.dto.CategoryReq;
import com.spring.pos2.entity.Category;
import com.spring.pos2.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<CategoryReq> findAll(){
        List<Category> categories = categoryRepo.findAll();
        return categories.stream()
                .map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }

    @Async("asyncTaskExecutor")//Async service
    public void findAllLong() throws InterruptedException {
        System.out.println("====Proses 1 Dijalankan=====");
        Thread.sleep(10000);//Coba untuk delay saja (u/ studi kasus contoh, seolah" proses ini sangat lama)
        System.out.println("====Proses 1 Selesai  ======");
//        List<Category> categories = categoryRepo.findAll();
//        return categories.stream()
//                .map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()))
//                .collect(Collectors.toList());
    }

    public Optional<CategoryReq> findById(Long id){
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        return categoryOptional.map(category -> new CategoryReq(category.getCategoryId(), category.getCategoryName()));
    }

    public CategoryReq create(CategoryReq categoryReq){
        Category category = new Category(categoryReq.getCategoryId(), categoryReq.getCategoryName());
        category = categoryRepo.save(category);
        return new CategoryReq(category.getCategoryId(), category.getCategoryName());
    }
}
