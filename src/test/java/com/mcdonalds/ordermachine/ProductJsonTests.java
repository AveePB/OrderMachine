package com.mcdonalds.ordermachine;

import com.mcdonalds.ordermachine.model.Product;
import com.mcdonalds.ordermachine.model.ProductType;

import org.assertj.core.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class ProductJsonTests {
    //Required objects:
    @Autowired
    private JacksonTester<Product> json = null;

    @Autowired
    private JacksonTester<Product[]> jsonArr = null;

    private Product[] products;

    @BeforeEach
    void setUp() {
        this.products = Arrays.array(
                new Product(1L, "#101", "Big Mac®", 39.0, ProductType.BURGER),
                new Product(2L, "#101", "Big Mac®", 39.0, ProductType.BURGER),
                new Product(3L, "#102", "Quarter Pounder®* with Cheese", 29.0, ProductType.BURGER),
                new Product(4L, "#102", "World Famous Fries®", 10.0, ProductType.FRIES)
        );
    }

    @Test
    void productSerializationTest() throws IOException {
        Product product = this.products[0];
        String expected = """
                {
                    "id": 1,
                    "receiptCode": "#101",
                    "name": "Big Mac®",
                    "price": 39.0,
                    "type": "BURGER"
                }
                """;

        //JSON FORM
        assertThat(this.json.write(product)).isStrictlyEqualToJson(expected);

        //Id
        assertThat(this.json.write(product)).hasJsonPathNumberValue("@.id");
        assertThat(this.json.write(product)).extractingJsonPathNumberValue("@.id").isEqualTo(1);

        //Receipt Code
        assertThat(this.json.write(product)).hasJsonPathStringValue("@.receiptCode");
        assertThat(this.json.write(product)).extractingJsonPathStringValue("@.receiptCode").isEqualTo("#101");

        //Name
        assertThat(this.json.write(product)).hasJsonPathStringValue("@.name");
        assertThat(this.json.write(product)).extractingJsonPathStringValue("@.name").isEqualTo("Big Mac®");

        //Price
        assertThat(this.json.write(product)).hasJsonPathNumberValue("@.price");
        assertThat(this.json.write(product)).extractingJsonPathNumberValue("@.price").isEqualTo(39.0);

        //Type
        assertThat(this.json.write(product)).hasJsonPathValue("@.type");
        assertThat(this.json.write(product)).extractingJsonPathStringValue("FRIES");
    }

    @Test
    void productDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": 1,
                    "receiptCode": "#101",
                    "name": "Big Mac®",
                    "price": 39.0,
                    "type": "BURGER"
                }
                """;

        Product product = this.json.parseObject(expected);

        //Id
        assertThat(product.getId()).isEqualTo(this.products[0].getId());

        //Receipt Code
        assertThat(product.getReceiptCode()).isEqualTo(this.products[0].getReceiptCode());

        //Name
        assertThat(product.getName()).isEqualTo(this.products[0].getName());

        //Price
        assertThat(product.getPrice()).isEqualTo(this.products[0].getPrice());

        //Type
        assertThat(product.getType()).isEqualTo(this.products[0].getType());
    }

    @Test
    void productListSerializationTest() throws IOException {
        String expected = """
                [
                    {"id": 1, "receiptCode": "#101", "name": "Big Mac®", "price": 39.0, "type": "BURGER"},
                    {"id": 2, "receiptCode": "#101", "name": "Big Mac®", "price": 39.0, "type": "BURGER"},
                    {"id": 3, "receiptCode": "#102", "name": "Quarter Pounder®* with Cheese", "price": 29.0, "type": "BURGER"},
                    {"id": 4, "receiptCode": "#102", "name": "World Famous Fries®", "price": 10.0, "type": "FRIES"}
                ]
                """;

        assertThat(this.jsonArr.write(this.products)).isStrictlyEqualToJson(expected);
    }

    @Test
    void productListDeserializationTest() throws IOException {
        String expected = """
                [
                    {"id": 1, "receiptCode": "#101", "name": "Big Mac®", "price": 39.0, "type": "BURGER"},
                    {"id": 2, "receiptCode": "#101", "name": "Big Mac®", "price": 39.0, "type": "BURGER"},
                    {"id": 3, "receiptCode": "#102", "name": "Quarter Pounder®* with Cheese", "price": 29.0, "type": "BURGER"},
                    {"id": 4, "receiptCode": "#102", "name": "World Famous Fries®", "price": 10.0, "type": "FRIES"}
                ]
                """;

        assertThat(this.jsonArr.parseObject(expected).length).isEqualTo(this.products.length);
    }
}
