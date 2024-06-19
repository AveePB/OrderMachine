package com.bpeeva.ordermachine.order.db;

import com.bpeeva.ordermachine.orderedproduct.db.OrderedProduct;
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

    @OneToMany
    private List<OrderedProduct> orderedProductList;
}
