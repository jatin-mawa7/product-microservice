package com.sumathi.productservice.controller;

import com.sumathi.productservice.model.ProductDto;
import com.sumathi.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @Hidden
    @GetMapping("/check/{id}")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(productService.checkAvailability(id));
    }
    @Hidden
    // check availability for requested quantity
    @GetMapping("/check/{id}/{quantity}")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id, @PathVariable Integer quantity) {
        return ResponseEntity.ok(productService.checkAvailabilityForQuantity(id, quantity));
    }

    @Hidden
    @GetMapping("/price/{id}")
    public ResponseEntity<Double> getPrice(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getPrice(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDTO) {
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }

    // reduce stock after successful payment
    @PutMapping("/reduce/{id}/{quantity}")
    public ResponseEntity<Void> reduceStock(@PathVariable Long id, @PathVariable Integer quantity) {
        boolean ok = productService.reduceStock(id, quantity);
        if (!ok) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

}



