package com.mcdonalds.ordermachine.controller;

import com.mcdonalds.ordermachine.model.Product;
import com.mcdonalds.ordermachine.repository.OrderedProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/orders")
public class OrderController {
    //Structures
    private final OrderedProductRepository orderedProductRepository;


    @Autowired //Constructor
    private OrderController(OrderedProductRepository orderedProductRepository) {
        this.orderedProductRepository = orderedProductRepository;
    }


    @GetMapping("/{requestedId}")
    private ResponseEntity<Product> findById(@PathVariable Long requestedId) {
        Optional<Product> productOptional = this.orderedProductRepository.findById(requestedId);

        if (productOptional.isPresent())
            return ResponseEntity.ok(productOptional.get());

        return ResponseEntity.notFound().build();
    }
}
