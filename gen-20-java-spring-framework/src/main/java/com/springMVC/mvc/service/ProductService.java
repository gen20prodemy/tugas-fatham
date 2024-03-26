package com.springMVC.mvc.service;

import com.springMVC.mvc.entity.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    private static List<Product> products = Arrays.asList(
            new Product(1, "Pensil", 5000),
            new Product(2, "Penghapus", 3000),
            new Product(3, "Bolpoin", 3000),
            new Product(4, "Handpone", 3000000),
            new Product(5, "Kacamata", 350000)
    );

    public List<Product> findAll(){
        return products;
    }
}
