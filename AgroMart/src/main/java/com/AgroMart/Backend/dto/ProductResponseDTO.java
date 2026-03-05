package com.AgroMart.Backend.dto;

import com.AgroMart.Backend.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private Integer stockQuantity;
    private String imageUrl;
    private List<String> additionalImages;
    private String brand;
    private Double rating;
    private ProductStatus status;
    private String categoryName; // ✅ show readable category name
}
