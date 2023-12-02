package com.mcdonalds.ordermachine.controller;

import com.mcdonalds.ordermachine.model.product.Product;
import com.mcdonalds.ordermachine.model.product.ProductType;
import com.mcdonalds.ordermachine.repository.OrderedProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/orders")
public class OrderController {
    //Structures
    private final OrderedProductRepository orderedProductRepository;


    @Autowired //Constructor with dependency injection.
    private OrderController(OrderedProductRepository orderedProductRepository) {
        this.orderedProductRepository = orderedProductRepository;
    }


    @GetMapping("/id={requestedId}")
    private ResponseEntity<Product> findById(@PathVariable Long requestedId) {
        Optional<Product> productOptional = this.orderedProductRepository.findById(requestedId);

        if (productOptional.isPresent())
            return ResponseEntity.ok(productOptional.get());

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/receiptCode={requestedReceiptCode}")
    private List<Product> findAllByReceiptCode(@PathVariable String requestedReceiptCode) {
        return this.orderedProductRepository.findByReceiptCode(requestedReceiptCode);
    }


    @GetMapping("/type={requestedType}")
    private List<Product> findAllByType(@PathVariable ProductType requestedType) {

        return this.orderedProductRepository.findByType(requestedType);
    }
}
