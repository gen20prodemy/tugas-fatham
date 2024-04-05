package com.spring.pos2.service;

import com.spring.pos2.dto.ProductReq;
import com.spring.pos2.entity.Product;
import com.spring.pos2.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductReq> findAll(){
        List<Product> products = productRepo.findAll();
        return products.stream()
                .map(product -> new ProductReq(product.getProductId(), product.getProductName(), product.getCategory().getCategoryId()))
                .collect(Collectors.toList());
    }

    public Optional<ProductReq> findById(Long id){
        Optional<Product> products = productRepo.findById(id);
        return products.map(product -> new ProductReq(product.getProductId(), product.getProductName(), product.getCategory().getCategoryId()));
    }

    public ProductReq create(ProductReq productReq){
        Product product = new Product(productReq.getProductId(), productReq.getProductName());
        product = productRepo.save(product);
        return new ProductReq(product.getProductId(), product.getProductName(), product.getCategory().getCategoryId());
    }
}
