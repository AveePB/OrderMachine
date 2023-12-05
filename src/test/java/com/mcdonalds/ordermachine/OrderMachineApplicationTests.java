package com.mcdonalds.ordermachine;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.mcdonalds.ordermachine.model.product.Product;
import com.mcdonalds.ordermachine.model.product.ProductType;
import com.mcdonalds.ordermachine.repository.OrderedProductRepository;

import net.minidev.json.JSONArray;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderMachineApplicationTests {
	//Required objects:
	@Autowired
	private final OrderedProductRepository orderedProductRepository = null;

	@Autowired
	private final TestRestTemplate restTemplate = null;


	@Test
	void shouldReturnAProductWhenDataIsSaved() {
		Product product = new Product(1L, "103", "Burgir", 32.0, ProductType.BURGER);
		this.orderedProductRepository.save(product);

		ResponseEntity<String> response = this.restTemplate.getForEntity("/orders/id=1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		//Id
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(Math.toIntExact(product.getId()));

		//Receipt Code
		String receiptCode = documentContext.read("$.receiptCode");
		assertThat(receiptCode).isEqualTo(product.getReceiptCode());

		//Name
		String name = documentContext.read("$.name");
		assertThat(name).isEqualTo(product.getName());

		//Price
		Double price = documentContext.read("$.price");
		assertThat(price).isEqualTo(product.getPrice());

		//Type
		String type = documentContext.read("$.type");
		assertThat(type).isEqualTo(product.getType().name());
	}


	@Test
	void shouldNotReturnAProductWhenDataIsNotSaved() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/orders/id=1", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}


	@Test
	void shouldReturnAListOfProductsByReceiptCodeWhenDataIsSaved() {
		Product[] products = new Product[]{
				new Product(null, "099", "Burgir with cheese", 34.5, ProductType.BURGER),
				new Product(null, "099", "Ice biom", 11.0, ProductType.ICE_CREAM)
		};

		this.orderedProductRepository.save(products[0]);
		this.orderedProductRepository.save(products[1]);

		ResponseEntity<String> response = this.restTemplate.getForEntity("/orders/receiptCode=099", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		//Product Count
		int productCount = documentContext.read("$.length()");
		assertThat(productCount).isEqualTo(2);

		//Names
		JSONArray names = documentContext.read("$..name");
		assertThat(names).containsExactlyInAnyOrder("Burgir with cheese", "Ice biom");

		//Prices
		JSONArray prices = documentContext.read("$..price");
		assertThat(prices).containsExactlyInAnyOrder(34.5, 11.0);

		//Types
		JSONArray types = documentContext.read("$..type");
		assertThat(types).containsExactlyInAnyOrder("BURGER", "ICE_CREAM");
	}


	@Test
	void shouldNotReturnAListOfProductsByReceiptCodeWhenDataIsNotSaved() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/orders/receiptCode=099", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		//Product Count
		int productCount = documentContext.read("$.length()");
		assertThat(productCount).isEqualTo(0);
	}


	@Test
	void shouldReturnAListOfProductsByTypeWhenDataIsSaved() {
		Product[] products = new Product[]{
				new Product(null, "019", "Burgir with cheese", 34.5, ProductType.BURGER),
				new Product(null, "399", "Lumberjack", 11.0, ProductType.BURGER),
				new Product(null, "399", "Icy Lumberjack", 11.0, ProductType.ICE_CREAM)
		};

		this.orderedProductRepository.save(products[0]);
		this.orderedProductRepository.save(products[1]);

		ResponseEntity<String> response = this.restTemplate.getForEntity("/orders/type=BURGER", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		//Product Count
		int productCount = documentContext.read("$.length()");
		assertThat(productCount).isEqualTo(2);

		//Receipt Codes
		JSONArray receiptCodes = documentContext.read("$..receiptCode");
		assertThat(receiptCodes).containsExactlyInAnyOrder("019", "399");

		//Names
		JSONArray names = documentContext.read("$..name");
		assertThat(names).containsExactlyInAnyOrder("Burgir with cheese", "Lumberjack");

		//Prices
		JSONArray prices = documentContext.read("$..price");
		assertThat(prices).containsExactlyInAnyOrder(34.5, 11.0);
	}


	@Test
	void shouldNotReturnAListOfProductsByTypeWhenDataIsNotSaved() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/orders/type=FRIES", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		//Product Count
		int productCount = documentContext.read("$.length()");
		assertThat(productCount).isEqualTo(0);
	}


	@Test
	void shouldCreateANewProduct() {
		Product newOrderedProduct = new Product(null, "101", "Marek Lumberjack", 31.3, ProductType.BURGER);
		ResponseEntity<String> response = this.restTemplate.postForEntity("/orders", newOrderedProduct, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(this.orderedProductRepository.count()).isEqualTo(1);
	}


	@Test
	void shouldUpdateProductsByNameAndType() {
		this.orderedProductRepository.save(new Product(null, "231", "Classic Mc Fries", 5.7, ProductType.FRIES));
		this.orderedProductRepository.save(new Product(null, "011", "Classic Mc Fries", 5.7, ProductType.FRIES));
		this.orderedProductRepository.save(new Product(null, "231", "Classic Mc Chicken", 35.7, ProductType.NUGGETS));

		//Performs an operation on database.
		Double updatedPrice = 12.946;
		ResponseEntity<String> response = this.restTemplate.exchange("/orders/name=Classic Mc Fries/type=FRIES/newPrice=" + updatedPrice.toString(), HttpMethod.PUT, null, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


		//Performs a second operation on database.
		ResponseEntity<String> secondResponse = this.restTemplate.exchange("/orders/name=Classic Mc Wrap/type=WRAP/newPrice=" + updatedPrice.toString(), HttpMethod.PUT, null, String.class);
		assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldDeleteProductsByReceiptCode() {
		this.orderedProductRepository.save(new Product(null, "003", "Cheeseburger", 7.3, ProductType.BURGER));
		this.orderedProductRepository.save(new Product(null, "003", "Mc Wrap", 7.3, ProductType.WRAP));
		this.orderedProductRepository.save(new Product(null, "013", "Cheeseburger", 7.3, ProductType.BURGER));

		//Performs an operation on database.
		String receiptCodeToDelete = "003";
		ResponseEntity<String> deleteResponse =  this.restTemplate.exchange("/orders/receiptCode=" + receiptCodeToDelete, HttpMethod.DELETE, null, String.class);

		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


		ResponseEntity<String> getResponse = this.restTemplate.getForEntity("/orders/receiptCode=" + receiptCodeToDelete, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());

		//Product Count
		int productCount = documentContext.read("$.length()");
		assertThat(productCount).isEqualTo(0);
	}

}
