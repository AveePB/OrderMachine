package com.bpeeva.ordermachine.order;

import com.bpeeva.ordermachine.orderedproduct.OrderedProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, precision = 2)
    private Double totalPrice;

    @OneToMany
    private List<OrderedProduct> orderedProductList;
}
