package com.jpa.jpa.repository;

import com.jpa.jpa.entity.NewProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewProductRepo extends JpaRepository<NewProduct, Integer> {

    //Native Query
    @Query(value = "SELECT * FROM new_product", nativeQuery = true)
    List<NewProduct> findAllUsingNativeQuery();

    //JPQL
    @Query("SELECT p FROM new_product p")
    List<NewProduct> getAllNewProductJPQL();
}
