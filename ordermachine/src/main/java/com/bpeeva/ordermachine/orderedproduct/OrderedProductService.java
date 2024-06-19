package com.bpeeva.ordermachine.orderedproduct;

import com.bpeeva.ordermachine.order.db.Order;
import com.bpeeva.ordermachine.order.db.OrderRepository;
import com.bpeeva.ordermachine.orderedproduct.db.OrderedProduct;
import com.bpeeva.ordermachine.orderedproduct.db.OrderedProductRepository;
import com.bpeeva.ordermachine.product.db.Product;
import com.bpeeva.ordermachine.product.db.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderedProductService {

    private final OrderedProductRepository orderedProductRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public List<String> getOrderedProducts(Integer orderNumber) {
        Optional<Order> order = orderRepo.findById(orderNumber);
        if (order.isEmpty()) return new ArrayList<>();

        return orderedProductRepo.findByOrder(order.get()).stream()
                .map(op -> op.getProduct().getName())
                .toList();
    }

    public boolean add(String productName, Integer orderNumber) {
        Optional<Product> product = productRepo.findByName(productName);
        Optional<Order> order = orderRepo.findById(orderNumber);

        if (product.isEmpty() || order.isEmpty()) return false;

        OrderedProduct orderedProduct = orderedProductRepo.save(OrderedProduct.builder()
                .order(order.get())
                .product(product.get())
                .build()
        );

        return orderedProduct.getId() != null;
    }

    public void remove(Integer orderNumber) {
        Optional<Order> order = orderRepo.findById(orderNumber);
        if (order.isEmpty()) return;

        orderedProductRepo.deleteByOrder(order.get());
    }
}
