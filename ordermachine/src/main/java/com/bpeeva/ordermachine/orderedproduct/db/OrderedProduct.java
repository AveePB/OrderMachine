package com.bpeeva.ordermachine.orderedproduct.db;

import com.bpeeva.ordermachine.product.db.Product;
import com.bpeeva.ordermachine.order.db.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderedProduct {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;
}
