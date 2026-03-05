package com.AgroMart.Backend.serviceimpl;

import com.AgroMart.Backend.repository.CategoryRepository;
import com.AgroMart.Backend.repository.ProductRepository;
import com.AgroMart.Backend.service.FileStorageService;
import com.AgroMart.Backend.dto.ProductRequestDTO;
import com.AgroMart.Backend.dto.ProductResponseDTO;
import com.AgroMart.Backend.model.Category;
import com.AgroMart.Backend.model.Product;
import com.AgroMart.Backend.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    public ProductServiceImpl(ProductRepository productRepository,
                             CategoryRepository categoryRepository,
                             FileStorageService fileStorageService){
        this.productRepository=productRepository;
        this.categoryRepository=categoryRepository;
        this.fileStorageService=fileStorageService;

    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequest, MultipartFile image, List<MultipartFile> additionalImages) {

        Category category =categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()->
                new RuntimeException("Category not found with id: "+productRequest.getCategoryId()));
        if(productRepository.existsByNameIgnoreCase(productRequest.getName())){
            throw new RuntimeException("Product with this name alredy exists");
        }


        String imageUrl=fileStorageService.saveFile(image,"product");


        List<String> additionalImageUrls=new ArrayList<>();
        if(additionalImages!=null &&  !additionalImages.isEmpty()){
            for(MultipartFile file:additionalImages){
              String filepath=fileStorageService.saveFile(file,"product");
              additionalImageUrls.add(filepath);
            }
        }

        Product product=new Product();


        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(imageUrl);
        product.setAdditionalImages(additionalImageUrls);
        product.setBrand(productRequest.getBrand());
        product.setDiscount(productRequest.getDiscount());
        product.setCategory(category);
        product.setPrice(productRequest.getPrice());
        product.setStatus(productRequest.getStatus());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setRating(productRequest.getRating());


        Product saved =productRepository.save(product);

        ProductResponseDTO dto=new ProductResponseDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setDescription(saved.getDescription());
        dto.setImageUrl(saved.getImageUrl());
        dto.setAdditionalImages(additionalImageUrls);
        dto.setBrand(saved.getBrand());
        dto.setDiscount(saved.getDiscount());
        dto.setCategoryName(saved.getCategory().getName());
        dto.setPrice(saved.getPrice());
        dto.setStatus(saved.getStatus());
        dto.setStockQuantity(saved.getStockQuantity());
        dto.setRating(saved.getRating());


        return  dto;

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products =productRepository.findAll();
        return  products.stream().map(p-> {
            ProductResponseDTO productResponse = new ProductResponseDTO();
            productResponse.setName(p.getName());
            productResponse.setId(p.getId());
            productResponse.setBrand(p.getBrand());
            productResponse.setDiscount(p.getDiscount());
            productResponse.setRating(p.getRating());
            productResponse.setPrice(p.getPrice());
            productResponse.setStatus(p.getStatus());
            productResponse.setStockQuantity(p.getStockQuantity());
            productResponse.setCategoryName(p.getCategory() != null ? p.getCategory().getName() : null);
            productResponse.setImageUrl(p.getImageUrl());
            productResponse.setAdditionalImages(p.getAdditionalImages());
            productResponse.setDescription(p.getDescription());
            return productResponse;
        }).collect(Collectors.toList());


    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByCategory(Long id){
        List<Product> products=productRepository.findByCategoryId(id);
        return  products.stream().map(p-> {
            ProductResponseDTO productResponse = new ProductResponseDTO();
            productResponse.setName(p.getName());
            productResponse.setId(p.getId());
            productResponse.setBrand(p.getBrand());
            productResponse.setDiscount(p.getDiscount());
            productResponse.setRating(p.getRating());
            productResponse.setPrice(p.getPrice());
            productResponse.setStatus(p.getStatus());
            productResponse.setStockQuantity(p.getStockQuantity());
            productResponse.setCategoryName(p.getCategory() != null ? p.getCategory().getName() : null);
            productResponse.setImageUrl(p.getImageUrl());
            productResponse.setAdditionalImages(p.getAdditionalImages());
            productResponse.setDescription(p.getDescription());
            return productResponse;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        Product product=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id: "+id));
        ProductResponseDTO productResponse=new ProductResponseDTO();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setAdditionalImages(product.getAdditionalImages());
        productResponse.setBrand(product.getBrand());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setCategoryName(product.getCategory().getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setStatus(product.getStatus());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setRating(product.getRating());
        return  productResponse;
    }



    public ProductResponseDTO updateProduct(Long id,ProductRequestDTO productRequest,MultipartFile image, List<MultipartFile> additionalImages){

        Product product=productRepository.findById(id).orElseThrow(()->new RuntimeException("category not found with id"+ id));
//        if(productRequest.getName()!=null && !productRequest.getName().equalsIgnoreCase(product.getName())){
//            boolean exists=productRepository.findAll()
//                    .stream().anyMatch(p->!p.getId().equals(id) && p.getName().equalsIgnoreCase(productRequest.getName()));
//            if(exists){
//                throw new RuntimeException("product with this name alredy Exists");
//            }
//        }
        if (productRepository.existsByNameIgnoreCase(productRequest.getName()) &&
                !productRequest.getName().equalsIgnoreCase(product.getName())) {
            throw new RuntimeException("Product with this name already exists");
        }


        if( !image.isEmpty() && image!=null ){
            fileStorageService.deleteFile(product.getImageUrl());
            String imageUrl=fileStorageService.saveFile(image,"product");
            product.setImageUrl(imageUrl);
        }
        List additionalImageUrls=new ArrayList<>();
        if( additionalImages!=null && !additionalImages.isEmpty() ){
            List<String> additionalImages1=product.getAdditionalImages();
            for(String image1:additionalImages1){
                fileStorageService.deleteFile(image1);
            }
            for(MultipartFile file:additionalImages){
                String filepath=fileStorageService.saveFile(file,"product");
                additionalImageUrls.add(filepath);
            }
        }

        product.setName(productRequest.getName());
        Category category=categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()->
                new RuntimeException("Category not found with id: "+productRequest.getCategoryId()));
        product.setCategory(category);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setBrand(productRequest.getBrand());
        product.setDiscount(productRequest.getDiscount());
        product.setCategory(category);
        product.setPrice(productRequest.getPrice());
        product.setStatus(productRequest.getStatus());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setRating(productRequest.getRating());
        product.setAdditionalImages(additionalImageUrls);
        Product saved=productRepository.save(product);

        ProductResponseDTO dto=new ProductResponseDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setDescription(saved.getDescription());
        dto.setImageUrl(saved.getImageUrl());
        dto.setAdditionalImages(saved.getAdditionalImages());
        dto.setBrand(saved.getBrand());
        dto.setDiscount(saved.getDiscount());
        dto.setCategoryName(saved.getCategory().getName());
        dto.setPrice(saved.getPrice());
        dto.setStatus(saved.getStatus());
        dto.setStockQuantity(saved.getStockQuantity());
        dto.setRating(saved.getRating());


        return  dto;
    }

    public void deleteProduct(Long id){
        Product product=productRepository.findById(id).orElseThrow(()->
                new RuntimeException("Product not found with id: "+ id));
        fileStorageService.deleteFile(product.getImageUrl());
        List<String> additionalImages=product.getAdditionalImages();
        for(String additionalImage:additionalImages){
            fileStorageService.deleteFile(additionalImage);
        }
        productRepository.delete(product);
    }
}
