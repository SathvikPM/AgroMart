package com.AgroMart.Backend.service;

import com.AgroMart.Backend.dto.ProductRequestDTO;
import com.AgroMart.Backend.dto.ProductResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequest, MultipartFile image, List<MultipartFile> additionalImages);
    List<ProductResponseDTO> getAllProducts();

    List<ProductResponseDTO> getProductsByCategory(Long id);

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO updateProduct(Long id,ProductRequestDTO productRequest,MultipartFile image, List<MultipartFile> additionalImages);

    void deleteProduct(Long id);
}
