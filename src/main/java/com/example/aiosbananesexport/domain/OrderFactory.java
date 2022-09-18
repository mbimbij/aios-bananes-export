package com.example.aiosbananesexport.domain;

import java.util.UUID;

public class OrderFactory {

    private final double pricePerKg;
    private final int orderIncrementQuantityKg;
    private final int orderMinQuantityKg;
    private final int orderMaxQuantityKg;

    public OrderFactory(double pricePerKg, int orderMinQuantityKg, int orderMaxQuantityKg, int orderIncrementQuantityKg) {
        this.pricePerKg = pricePerKg;
        this.orderMinQuantityKg = orderMinQuantityKg;
        this.orderMaxQuantityKg = orderMaxQuantityKg;
        this.orderIncrementQuantityKg = orderIncrementQuantityKg;
    }

    Order createOrder(PlaceOrderCommand placeOrderCommand) {
        return new Order(generateId(),
                         placeOrderCommand.getFirstName(),
                         placeOrderCommand.getLastName(),
                         placeOrderCommand.getAddress(),
                         placeOrderCommand.getPostalCode(),
                         placeOrderCommand.getCity(),
                         placeOrderCommand.getCountry(),
                         placeOrderCommand.getDeliveryDate(),
                         placeOrderCommand.getQuantityKg(),
                         orderIncrementQuantityKg,
                         orderMinQuantityKg,
                         orderMaxQuantityKg,
                         pricePerKg * placeOrderCommand.getQuantityKg());
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
