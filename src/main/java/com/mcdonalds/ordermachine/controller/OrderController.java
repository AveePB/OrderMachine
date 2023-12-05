package com.mcdonalds.ordermachine.controller;

import com.mcdonalds.ordermachine.model.product.Product;
import com.mcdonalds.ordermachine.model.product.ProductType;
import com.mcdonalds.ordermachine.repository.OrderedProductRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
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
        //Accesses the resource from database.
        Optional<Product> productOptional = this.orderedProductRepository.findById(requestedId);

        //Checks if resource has been found.
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


    @PostMapping
    private ResponseEntity<String> createProduct(@RequestBody Product product) {
        Product newProduct = new Product(null, product.getReceiptCode(), product.getName(), product.getPrice(), product.getType());

        //Adds resource to database.
        Product createdProduct = this.orderedProductRepository.save(newProduct);

        //Creates resource location.
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").
                buildAndExpand(createdProduct.getId()).
                toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("/name={requestedProductName}/type={requestedProductType}/newPrice={newRequestedProductPrice}")
    private ResponseEntity<String> updateProductsByPrice(@PathVariable String requestedProductName, @PathVariable ProductType requestedProductType, @PathVariable Double newRequestedProductPrice) {
        //Accesses the resources from database.
        List<Product> requestedProducts = this.orderedProductRepository.findByNameAndType(requestedProductName, requestedProductType);

        //Checks if found any resource.
        if (requestedProducts.size() == 0)
            return ResponseEntity.notFound().build();

        for (Product updatedProduct: requestedProducts) {
            //Updates & saves each requested product.
            updatedProduct.setPrice(newRequestedProductPrice);
            this.orderedProductRepository.save(updatedProduct);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/receiptCode={requestedReceiptCode}")
    private ResponseEntity<String> deleteProductsByReceiptCode(@PathVariable String requestedReceiptCode) {
        this.orderedProductRepository.deleteAllByReceiptCode(requestedReceiptCode);

        return ResponseEntity.noContent().build();
    }

}
