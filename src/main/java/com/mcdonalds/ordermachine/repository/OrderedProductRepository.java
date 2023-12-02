package com.mcdonalds.ordermachine.repository;

import com.mcdonalds.ordermachine.model.product.Product;
import com.mcdonalds.ordermachine.model.product.ProductType;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderedProductRepository extends JpaRepository<Product, Long> {
    //Spring Boot generated functions:
    List<Product> findByReceiptCode(String receiptCode);
    List<Product> findByType(ProductType type);
}