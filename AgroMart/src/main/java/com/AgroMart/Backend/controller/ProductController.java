package com.AgroMart.Backend.controller;

import com.AgroMart.Backend.dto.ProductRequestDTO;
import com.AgroMart.Backend.dto.ProductResponseDTO;
import com.AgroMart.Backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;

    public  ProductController(ProductService productService){
        this.productService=productService;

    }




    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestPart("product")ProductRequestDTO productRequest,
            @RequestPart(value = "image",required = false)MultipartFile image,
            @RequestPart(value = "additionalImages",required = false)List<MultipartFile> additionalImages) {

        ProductResponseDTO created =productService.createProduct(productRequest,image,additionalImages);
        return  ResponseEntity.ok(created);

    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> products=productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable  Long id){
        ProductResponseDTO productResponse=productService.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping(value = "/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,@RequestPart("product") ProductRequestDTO productRequest,
                                                            @RequestPart(value="image",required = false)MultipartFile image,
                                                            @RequestPart(value = "additionalImages",required = false)List<MultipartFile> additionalImages){
        ProductResponseDTO productResponse=productService.updateProduct(id,productRequest,image,additionalImages);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted Sucessfully");
    }




}
