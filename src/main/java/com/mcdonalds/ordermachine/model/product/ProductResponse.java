package com.mcdonalds.ordermachine.model.product;

public record ProductResponse(String receiptCode, String name, Double Price, ProductType type) {

}
