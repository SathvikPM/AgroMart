package com.AgroMart.Backend.repository;

import com.AgroMart.Backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByNameIgnoreCase(String name);

    List<Product> findByCategoryId(Long categoryId);
}
