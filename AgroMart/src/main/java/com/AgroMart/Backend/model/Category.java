package com.AgroMart.Backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Category name is mandatory")
    @Column(unique = true,nullable = false)
    private String name;

    private String description;

    private String imageUrl;
}
