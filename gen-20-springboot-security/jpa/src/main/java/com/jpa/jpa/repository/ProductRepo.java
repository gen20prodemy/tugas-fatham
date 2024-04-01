package com.jpa.jpa.repository;

import com.jpa.jpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    //List<Product> findByName(String name);
}
