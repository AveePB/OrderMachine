package com.bpeeva.ordermachine.orderedproduct.db;

import com.bpeeva.ordermachine.order.db.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Integer> {

    List<OrderedProduct> findByOrder(Order order);

    @Transactional
    void deleteByOrder(Order order);
}
