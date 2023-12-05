# McDonald's Order Machine

## Table of contents
1. [Introduction](#introduction)
2. [Representational State Transfer "REST"](#rest)
3. [Create, Read, Update and Delete "CRUD"](#crud)
4. [Spring Web Controllers](#spring_web_controllers)
    - [GET Method](#get_method)
    - [POST Method](#post_method)
    - [PUT Method](#put_method)
    - [DELETE Method](#delete_method)

## Introduction <a name="introduction"></a>
Welcome to the Spring Boot McDonald's Order Machine project! In this endeavor, we delve into the realm of 
software development using Spring Boot to create a streamlined and efficient ordering system inspired by 
the world-renowned McDonald's.

This project serves as an immersive learning experience, providing a platform to grasp fundamental skills 
in software development. Through the utilization of Spring Boot, a powerful framework for building Java-based 
applications, we aim to craft a functional and user-friendly ordering system.

## Representational State Transfer "REST" <a name="rest"></a>
Representational State Transfer (REST) is an architectural style that defines a set of constraints to be used 
for creating web services. REST API is a way of accessing web services in a simple and flexible way without
having any processing. Web application's data might be accessed via an API, and are often stored in a persistent 
data store, such as a database.

## Create, Read, Update and Delete "CRUD" <a name="crud"></a>
A frequently mentioned concept when speaking about REST is CRUD. CRUD stands for “Create, Read, Update, and Delete”.
These are the four basic operations that can be performed on objects in a data store. We’ll learn that REST has 
specific guidelines for implementing each one.

| Operation | HTTP Method | Response Status |
|-----------|-------------|-----------------|
| Read      | GET         | 200 (OK)        |
| Create    | POST        | 201 (CREATED)   |
| Update    | PUT         | 204 (NO DATA)   |
| Delete    | DELETE      | 204 (NO DATA)   |

## Spring Web Controllers <a name="spring_web_controllers"></a>
In Spring Web, Requests are handled by Controllers. The Controller gets injected into Spring Web, which routes API
requests (handled by the Controller) to the correct method. Since REST says that endpoints should use the HTTP 
methods, you need to tell Spring to route requests to the method only on specified requests

### GET Method <a name="get_method"></a>
```
    @GetMapping("/id={requestedId}")
    private ResponseEntity<Product> findById(@PathVariable Long requestedId) {
        //Accesses the resource from database.
        Optional<Product> productOptional = this.orderedProductRepository.findById(requestedId);

        //Checks if resource has been found.
        if (productOptional.isPresent())
            return ResponseEntity.ok(productOptional.get());

        return ResponseEntity.notFound().build();
    }
```

### POST Method <a name="post_method"></a>
```
    @PostMapping
    private ResponseEntity<String> createProduct(@RequestBody Product product) {
        Product newProduct = new Product(null, product.getReceiptCode(), product.getName(), product.getPrice(), product.getType());

        //Adds resource to database.
        Product createdProduct = this.orderedProductRepository.saveAndFlush(newProduct);

        //Creates resource location.
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").
                buildAndExpand(createdProduct.getId()).
                toUri();

        return ResponseEntity.created(location).build();
    }
```

### PUT Method <a name="put_method"></a>
```
   @PutMapping("/name={requestedProductName}/type={requestedProductType}/newPrice={newRequestedProductPrice}")
    private ResponseEntity<String> updateProductsByPrice(@PathVariable String requestedProductName, @PathVariable ProductType requestedProductType, @PathVariable Double newRequestedProductPrice) {
        //Accesses the resources from database.
        List<Product> requestedProducts = this.orderedProductRepository.findByNameAndType(requestedProductName, requestedProductType);

        //Checks if found any resource.
        if (requestedProducts.size() == 0)
            return ResponseEntity.notFound().build();

        for (Product updatedProduct: requestedProducts) {
            //Updates & saves each requested product.
            updatedProduct.setPrice(newRequestedProductPrice);
            this.orderedProductRepository.save(updatedProduct);
        }

        return ResponseEntity.noContent().build();
    }
```

### DELETE Method <a name="delete_method"></a>
```
    @DeleteMapping("/receiptCode={requestedReceiptCode}")
    private ResponseEntity<String> deleteProductsByReceiptCode(@PathVariable String requestedReceiptCode) {
        this.orderedProductRepository.deleteAllByReceiptCode(requestedReceiptCode);

        return ResponseEntity.noContent().build();
    }
```
