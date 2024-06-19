package com.bpeeva.ordermachine.orderedproduct;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordered-prod/{orderId}")
@RequiredArgsConstructor
public class OrderedProductController {

    private final OrderedProductService orderedProductService;

    @GetMapping
    private ResponseEntity<List<String>> getAllOrderedProducts(@PathVariable Integer orderId) {

        return ResponseEntity.ok(orderedProductService.getOrderedProducts(orderId));
    }

    @PostMapping
    private ResponseEntity<String> addOrderedProduct(@PathVariable Integer orderId, @RequestBody OrderedProductDTO request) {
        if (orderedProductService.add(request.productName(), orderId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping
    private ResponseEntity<String> removeOrderedProducts(@PathVariable Integer orderId) {
        orderedProductService.remove(orderId);
        return ResponseEntity.noContent().build();
    }
}
