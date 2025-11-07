package com.sumathi.productservice.service;

import com.sumathi.productservice.model.ProductDto;
import com.sumathi.productservice.entity.Product;
import com.sumathi.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public boolean checkAvailability(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return product != null && product.getQuantity() > 0;
    }

    public double getPrice(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return (product != null) ? product.getPrice() : 0.0;
    }

    public ProductDto addProduct(ProductDto dto) {
        Product product = new Product(); // use no-arg constructor
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());

        Product saved = productRepository.save(product);
        return convertToDto(saved);
    }

    public void reduceStock(Long id, int quantity) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null && product.getQuantity() >= quantity) {
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
        }
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        return dto;
    }
}


