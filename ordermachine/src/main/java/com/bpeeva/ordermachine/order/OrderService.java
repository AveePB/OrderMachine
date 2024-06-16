package com.bpeeva.ordermachine.order;

import com.bpeeva.ordermachine.order.db.Order;
import com.bpeeva.ordermachine.order.db.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;

    public List<Integer> getAll() {
        return orderRepo.findAll().stream()
                .map(Order::getId)
                .toList();
    }

    public Integer add() {
        return orderRepo.save(new Order(null, null))
                .getId();
    }

    public void remove(Integer id) {
        orderRepo.deleteById(id);
    }
}
