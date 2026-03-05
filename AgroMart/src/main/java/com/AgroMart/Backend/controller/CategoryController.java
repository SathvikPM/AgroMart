package com.AgroMart.Backend.controller;

import com.AgroMart.Backend.dto.CategoryResponseDTO;
import com.AgroMart.Backend.dto.CategoryRequestDTO;
import com.AgroMart.Backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid  @RequestPart("category") CategoryRequestDTO categoryRequest,
                                                              @RequestPart("file") MultipartFile file){

        CategoryResponseDTO categoryResponseDTO =categoryService.createCategory(categoryRequest,file);
        return  ResponseEntity.ok(categoryResponseDTO);

    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> categories=categoryService.getAllCategories();
        return  ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
                CategoryResponseDTO category=categoryService.getCategoryById(id);
                return  ResponseEntity.ok(category);
    }

    @PutMapping(value="/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id,
                                                              @RequestPart("category") CategoryRequestDTO categoryRequest,
                                                              @RequestPart(value = "file",required = false)MultipartFile file){
    CategoryResponseDTO updatedCategory=categoryService.updateCategory(id, categoryRequest,file);
    return ResponseEntity.ok(updatedCategory);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
       categoryService.deleteCategory(id);
       return ResponseEntity.ok("category deleted successfully");
    }

}
