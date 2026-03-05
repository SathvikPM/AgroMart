package com.AgroMart.Backend.dto;

import com.AgroMart.Backend.model.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

private Long id;
    @NotBlank
    private String name;

    private String description;


    @NotNull
    private Double price;

    private Double discount;

    private Integer stockQuantity;


    private String brand;

    private Double rating;

    private ProductStatus status;


    @NotNull
    private Long categoryId; // ✅ only store ID, not full object
}
