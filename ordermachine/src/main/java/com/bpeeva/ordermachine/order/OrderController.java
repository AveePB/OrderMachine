package com.bpeeva.ordermachine.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    private ResponseEntity<List<Integer>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    private ResponseEntity<Integer> addOrder() {
        return ResponseEntity.ok(orderService.add());
    }

    @DeleteMapping("/{orderId}")
    private ResponseEntity<String> removeOrder(@PathVariable Integer orderId) {
        orderService.remove(orderId);
        return ResponseEntity.noContent().build();
    }
}
