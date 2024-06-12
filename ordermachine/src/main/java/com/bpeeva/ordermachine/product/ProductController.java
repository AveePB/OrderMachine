package com.bpeeva.ordermachine.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class ProductController {

    @GetMapping
    private ResponseEntity<String> getAllProducts() {

        return null;
    }

    @PostMapping
    private ResponseEntity<String> addProduct(@RequestBody ProductDTO request) {

        return null;
    }

    @PatchMapping
    private ResponseEntity<String> updateProductPrice(@RequestBody ProductDTO request) {

        return null;
    }

    @DeleteMapping("/{productName}")
    private ResponseEntity<String> removeProduct(@PathVariable String productName) {

        return null;
    }
}
