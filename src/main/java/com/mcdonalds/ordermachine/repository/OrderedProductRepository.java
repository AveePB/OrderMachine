package com.mcdonalds.ordermachine.repository;

import com.mcdonalds.ordermachine.model.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderedProductRepository extends CrudRepository<Product, Long> {

}