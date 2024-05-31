package com.project.project.service;

import com.project.project.dto.CategoryReq;
import com.project.project.responHandler.CustomResponse;
import com.project.project.dto.ProductReq;
import com.project.project.entity.Category;
import com.project.project.entity.Product;
import com.project.project.repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryService categoryService;

    public ResponseEntity<List<ProductReq>> getAllProducts(String title, String sortBy, String sortOrder){
        List<Product> products;
        if(title == null){
            products = productRepo.findAll(Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        } else {
            products = productRepo.findByTitleLikeIgnoreCase(title);
        }
        if(products.isEmpty()){
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<ProductReq> productReqs = products.stream()
                .map(product -> convertToProductReq(product))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productReqs);
    }


    public ResponseEntity<List<ProductReq>> getProductsByCategoryId(int categoryId){
        try{
            CategoryReq categoryReq = categoryService.getCategoryById(categoryId);

            if (categoryReq != null) {
                Category category = new Category();
                category.setCategoryId(categoryId);
                category.setCategoryName(categoryReq.getCategoryName());

                List<Product> productList = productRepo.findByCategory(category);

                if (productList.isEmpty()){
                    return ResponseEntity.ok(Collections.emptyList());
                }

                List<ProductReq> productReqs = productList.stream()
                        .map(product -> convertToProductReq(product))
                        .collect(Collectors.toList());

                return ResponseEntity.ok(productReqs);
            }else{
                return ResponseEntity.ok(Collections.emptyList());
            }
        } catch (Exception e){
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    public CustomResponse createProduct(ProductReq productReq){
        try {
            // Validasi untuk title
            String titleRegex = "^[a-zA-Z0-9 ]+$";
            if (productReq.getTitle() == null || productReq.getTitle().isEmpty() || productReq.getTitle().length() > 255
                    || !productReq.getTitle().matches(titleRegex)) {
                throw new IllegalArgumentException("Title cannot be empty or exceeds the maximum length (255 characters), or contain special characters");
            }
            // Validasi untuk image
            if (productReq.getImage() == null || productReq.getImage().isEmpty() || productReq.getImage().length() > 255) {
                throw new IllegalArgumentException("Image URL cannot be empty or exceeds the maximum length (255 characters)");
            }
            // Validasi untuk price
            if (productReq.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero or not null");
            }
            Product product = convertToProductEntity(productReq);
            product = productRepo.save(product);
            CustomResponse response = new CustomResponse();
            response.setStatus("ok");
            response.setMessage("success");
            return response;
        }catch (IllegalArgumentException e){
            CustomResponse response = new CustomResponse();
            response.setStatus("failed");
            response.setMessage("Validation error: " + e.getMessage());
            return response;
        }catch (Exception e){// Untuk menangkap category id yang tidak ada, internal server error
            CustomResponse response = new CustomResponse();
            response.setStatus("failed");
            response.setMessage("Data was not added successfully: " +e.getMessage());
            return response;
        }
    }

    public CustomResponse deleteProduct(int productId) {
        try {
            Optional<Product> optionalProduct = productRepo.findById(productId);
            if (optionalProduct.isPresent()) {
                productRepo.deleteById(productId);
                CustomResponse response = new CustomResponse();
                response.setStatus("ok");
                response.setMessage("success");
                return response;
            } else {
                CustomResponse response = new CustomResponse();
                response.setStatus("failed");
                response.setMessage("product not found with ID: " + productId);
                return response;
            }
        } catch (Exception e) {
            CustomResponse response = new CustomResponse();
            response.setStatus("failed");
            response.setMessage("data was not deleted successfully, "+e.getMessage());
            return response;
        }
    }

    public CustomResponse updateProduct(int productId, ProductReq productReq) {
        CustomResponse response = new CustomResponse();
        try {
            // Cek apakah produk dengan ID yang diberikan ada di database
            Optional<Product> existingProductOptional = productRepo.findById(productId);
            if (existingProductOptional.isEmpty()) {
                response.setStatus("failed");
                response.setMessage("Product id not found");
                return response;
            }

            // Update data produk
            Product existingProduct = existingProductOptional.get();
            existingProduct.setTitle(productReq.getTitle());
            existingProduct.setImage(productReq.getImage());
            existingProduct.setPrice(productReq.getPrice());

            // Validasi untuk title
            String titleRegex = "^[a-zA-Z0-9 ]+$";
            if (productReq.getTitle() == null || productReq.getTitle().isEmpty() || productReq.getTitle().length() > 255
                    || !productReq.getTitle().matches(titleRegex)) {
                throw new IllegalArgumentException("Title cannot be empty or exceeds the maximum length (255 characters), or contain special characters");
            }

            // Validasi untuk image
            if (productReq.getImage() == null || productReq.getImage().isEmpty() || productReq.getImage().length() > 255) {
                throw new IllegalArgumentException("Image URL cannot be empty or exceeds the maximum length (255 characters)");
            }
            // Validasi untuk price
            if (productReq.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero or not null");
            }

            CategoryReq categoryReq = categoryService.getCategoryById(productReq.getCategoryId());
            if (categoryReq == null) {
                response.setStatus("failed");
                response.setMessage("Category id not found");
                return response;
            }
            // Set the updated category for the product
            Category category = new Category();
            category.setCategoryId(categoryReq.getCategoryId());
            category.setCategoryName(categoryReq.getCategoryName());
            existingProduct.setCategory(category);

            productRepo.save(existingProduct);

            response.setStatus("ok");
            response.setMessage("success");
        } catch (EntityNotFoundException e){//Check for 404 not found, or invalid JSON parse format
            response.setStatus("failed");
            response.setMessage("Category id not found");
        } catch (IllegalArgumentException e) {//Data out of bound
            response.setStatus("failed");
            response.setMessage("data was not updated successfully: "+ e.getMessage());
        }
        return response;
    }

    public ResponseEntity<List<ProductReq>> getProductById(int productId){
        try {
            Optional<Product> productOptional = productRepo.findById(productId);
            if (productOptional.isPresent()) {
                ProductReq productReq = convertToProductReq(productOptional.get());
                List<ProductReq> productList = Collections.singletonList(productReq);
                return ResponseEntity.ok(productList);
            } else {
                return ResponseEntity.ok(Collections.emptyList());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Product convertToProductEntity(ProductReq productReq) {
        Product product = new Product();
        product.setTitle(productReq.getTitle());
        product.setImage(productReq.getImage());
        product.setPrice(productReq.getPrice());

        CategoryReq categoryReq = categoryService.getCategoryById(productReq.getCategoryId());
        Category category = new Category();
        category.setCategoryId(categoryReq.getCategoryId());
        category.setCategoryName(categoryReq.getCategoryName());

        product.setCategory(category);
        return product;
    }

    private ProductReq convertToProductReq(Product product) {
        ProductReq productReq = new ProductReq();
        productReq.setProductId(product.getProductId());
        productReq.setTitle(product.getTitle());
        productReq.setImage(product.getImage());
        productReq.setPrice(product.getPrice());
        productReq.setCategoryId(product.getCategory().getCategoryId());

        // Ambil categoryName dari CategoryService berdasarkan categoryId
        CategoryReq categoryReq = categoryService.getCategoryById(product.getCategory().getCategoryId());
        productReq.setCategoryName(categoryReq.getCategoryName());

        return productReq;
    }



}
