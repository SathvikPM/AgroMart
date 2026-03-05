package com.AgroMart.Backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {
    private Long id;
    @NotBlank(message = "Category name is mandatory")
    private String name;
    private String description;
}
