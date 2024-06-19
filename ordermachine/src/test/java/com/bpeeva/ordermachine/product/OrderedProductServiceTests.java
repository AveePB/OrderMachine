package com.bpeeva.ordermachine.product;

import com.bpeeva.ordermachine.order.db.Order;
import com.bpeeva.ordermachine.order.db.OrderRepository;
import com.bpeeva.ordermachine.orderedproduct.OrderedProductService;
import com.bpeeva.ordermachine.orderedproduct.db.OrderedProduct;
import com.bpeeva.ordermachine.orderedproduct.db.OrderedProductRepository;
import com.bpeeva.ordermachine.product.db.Product;
import com.bpeeva.ordermachine.product.db.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderedProductServiceTests {

    @Autowired
    private final OrderedProductService orderedProductService = null;

    @Autowired private final OrderedProductRepository orderedProductRepo = null;
    @Autowired private final ProductRepository productRepo = null;
    @Autowired private final OrderRepository orderRepo = null;

    private Product mcNuggets, frenchFries;
    private OrderedProduct orderedNuggets, orderedFries;
    private Order order;

    @BeforeEach
    void setUp() {
        mcNuggets = productRepo.save(new Product(null, "Mc Nuggets", 12.4, null));
        frenchFries = productRepo.save(new Product(null, "French Fries", 5.7, null));

        order = orderRepo.save(new Order(null, null)); //Saves sth to ordered product repo ????

        orderedNuggets = orderedProductRepo.save(new OrderedProduct(null, mcNuggets, order));
        orderedFries = orderedProductRepo.save(new OrderedProduct(null, frenchFries, order));
    }

    @Test
    void shouldReturnAllProductsFromOrder() {
        //Arrange
        Order emptyOrder = orderRepo.save(new Order(null, null));

        //Act
        int currOrderSize = orderedProductService.getOrderedProducts(order.getId()).size();
        int emptyOrderSize = orderedProductService.getOrderedProducts(emptyOrder.getId()).size();
        int dummyOrderSize = orderedProductService.getOrderedProducts(-1).size();

        //Assert
        assertThat(currOrderSize).isEqualTo(2);
        assertThat(emptyOrderSize).isEqualTo(0);
        assertThat(dummyOrderSize).isEqualTo(0);
    }

    @Test
    void shouldSuccessfullyAddAProductToTheOrder() {
        //Act
        boolean areMcNuggetsAdded = orderedProductService.add(mcNuggets.getName(), order.getId());
        boolean areMcNuggetsAdded2 = orderedProductService.add(mcNuggets.getName(), order.getId());

        //Assert
        assertThat(areMcNuggetsAdded).isTrue();
        assertThat(areMcNuggetsAdded2).isTrue();
        assertThat(orderedProductRepo.findByOrder(order).size()).isEqualTo(4);
    }

    @Test
    void shouldFailToAddAProductToTheOrder() {
        //Act
        boolean areMcNuggetsAdded = orderedProductService.add(mcNuggets.getName(), -1);
        boolean areMcNuggetsAdded2 = orderedProductService.add("Dummy Product Name", order.getId());

        //Assert
        assertThat(areMcNuggetsAdded).isFalse();
        assertThat(areMcNuggetsAdded2).isFalse();
        assertThat(orderedProductRepo.findByOrder(order).size()).isEqualTo(2);
    }

    @Test
    void shouldRemoveAllOrderedProducts() {
        //Act
        orderedProductService.remove(order.getId());
        orderedProductService.remove(-1);

        //Assert
        assertThat(orderedProductRepo.findByOrder(order).size()).isEqualTo(0);
    }
}
