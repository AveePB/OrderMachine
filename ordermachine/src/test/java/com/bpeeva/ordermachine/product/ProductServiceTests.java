package com.bpeeva.ordermachine.product;

import com.bpeeva.ordermachine.product.db.Product;
import com.bpeeva.ordermachine.product.db.ProductRepository;
import com.bpeeva.ordermachine.product.db.ProductService;
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
public class ProductServiceTests {

    @Autowired
    private final ProductRepository productRepo = null;
    @Autowired
    private final ProductService productService = null;

    private Product cheeseburger, frenchFries;

    @BeforeEach
    void setUp() {
        cheeseburger = productRepo.save(new Product(null, "Cheeseburger", 2.95,null));
        frenchFries = productRepo.save(new Product(null, "French Fries", 2.90, null));
    }

    @Test
    void shouldAddANewProduct() {
        //Arrange
        Product mcMuffin = new Product(null, "McMuffin", 3.99, null);
        Product mcNuggets = new Product(null, "McNuggets", 6.89, null);

        //Act
        boolean isMcMuffinSaved = productService.add(mcMuffin.getName(), mcMuffin.getPrice());
        boolean isMcNuggetsSaved = productService.add(mcNuggets.getName(), mcNuggets.getPrice());

        //Assert
        assertThat(productRepo.findByName(mcMuffin.getName()).isPresent()).isTrue();
        assertThat(isMcMuffinSaved).isTrue();

        assertThat(productRepo.findByName(mcNuggets.getName()).isPresent()).isTrue();
        assertThat(isMcNuggetsSaved).isTrue();
    }

    @Test
    void shouldFailToAddANewProduct() {
        //Act
        boolean isCheeseburgerSaved = productService.add(cheeseburger.getName(), cheeseburger.getPrice());
        boolean isFrenchFriesSaved = productService.add("Large French Fries", -423.0);

        //Assert
        assertThat(productRepo.count()).isEqualTo(2);

        assertThat(isCheeseburgerSaved).isFalse();
        assertThat(isFrenchFriesSaved).isFalse();
    }

    @Test
    void shouldUpdateThePriceOfAProduct() {
        //Act
        boolean isCheeseburgerUpdated = productService.updatePrice(cheeseburger.getName(), 5.76);
        boolean isFrenchFriesUpdated = productService.updatePrice(frenchFries.getName(), 3.79);

        //Assert
        assertThat(productRepo.findByName(cheeseburger.getName()).get().getPrice()).isNotEqualTo(cheeseburger.getPrice());
        assertThat(isCheeseburgerUpdated).isTrue();

        assertThat(productRepo.findByName(frenchFries.getName()).get().getPrice()).isNotEqualTo(frenchFries.getPrice());
        assertThat(isFrenchFriesUpdated).isTrue();
    }

    @Test
    void shouldFailToUpdateThePriceOfAProduct() {
        //Act
        boolean isCheeseburgerUpdated = productService.updatePrice(cheeseburger.getName(), -5.76);
        boolean isFrenchFriesUpdated = productService.updatePrice("fReNcH fRiEs", 3.79);

        //Assert
        assertThat(productRepo.findByName(cheeseburger.getName()).get().getPrice()).isNotEqualTo(-5.76);
        assertThat(isCheeseburgerUpdated).isFalse();

        assertThat(productRepo.findByName(frenchFries.getName()).get().getPrice()).isNotEqualTo(3.79);
        assertThat(isFrenchFriesUpdated).isFalse();
    }

    @Test
    void shouldDeleteAProduct() {
        //Act
        productService.remove(cheeseburger.getName());
        productService.remove(frenchFries.getName());

        //Assert
        assertThat(productRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldTryToDeleteAProduct() {
        //Act
        productService.remove("ChEeSeBuRgEr");
        productService.remove("FrEnCh FrIeS");

        //Assert
        assertThat(productRepo.count()).isEqualTo(2);
    }
}
