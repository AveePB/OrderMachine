package com.mcdonalds.ordermachine.repository;

import com.mcdonalds.ordermachine.model.Product;

import com.mcdonalds.ordermachine.model.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderedProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    //Spring Boot generated functions:
    Page<Product> findByReceiptCode(String ReceiptCode, PageRequest pageRequest);
    Page<Product> findByType(ProductType type, PageRequest pageRequest);
}