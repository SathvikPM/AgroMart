package com.AgroMart.Backend.repository;

import com.AgroMart.Backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByNameIgnoreCase(String name);
}
