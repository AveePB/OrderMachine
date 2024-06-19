package com.bpeeva.ordermachine.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    private ResponseEntity<List<ProductDTO>> getAllProducts() {

        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping
    private ResponseEntity<String> addProduct(@RequestBody ProductDTO request) {
        boolean isAdded = productService.add(request.name(), request.price());
        if (isAdded) return ResponseEntity.noContent().build();

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping
    private ResponseEntity<String> updateProductPrice(@RequestBody ProductDTO request) {
        boolean isUpdated = productService.updatePrice(request.name(), request.price());
        if (isUpdated) return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{productName}")
    private ResponseEntity<String> removeProduct(@PathVariable String productName) {
        productService.remove(productName);
        return ResponseEntity.noContent().build();
    }
}
