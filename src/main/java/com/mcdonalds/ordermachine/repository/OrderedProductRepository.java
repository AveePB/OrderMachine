package com.mcdonalds.ordermachine.repository;

import com.mcdonalds.ordermachine.model.product.Product;
import com.mcdonalds.ordermachine.model.product.ProductType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface OrderedProductRepository extends JpaRepository<Product, Long> {
    //Spring Boot generated functions:
    Page<Product> findByReceiptCode(String ReceiptCode, PageRequest pageRequest);
    Page<Product> findByType(ProductType type, PageRequest pageRequest);
}