package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.utils.IdGenerator;

public class OrderFactory {

    private final double pricePerKgEuro;
    private final int orderIncrementQuantityKg;
    private final int orderMinQuantityKg;
    private final int orderMaxQuantityKg;
    private final int deliveryMinDelayDays;

    public OrderFactory(double pricePerKgEuro, int orderMinQuantityKg, int orderMaxQuantityKg, int orderIncrementQuantityKg, int deliveryMinDelayDays) {
        this.pricePerKgEuro = pricePerKgEuro;
        this.orderMinQuantityKg = orderMinQuantityKg;
        this.orderMaxQuantityKg = orderMaxQuantityKg;
        this.orderIncrementQuantityKg = orderIncrementQuantityKg;
        this.deliveryMinDelayDays = deliveryMinDelayDays;
    }

    Order createOrder(PlaceOrderCommand placeOrderCommand) {
        return new Order(IdGenerator.newId(),
                         placeOrderCommand.getFirstName(),
                         placeOrderCommand.getLastName(),
                         placeOrderCommand.getAddress(),
                         placeOrderCommand.getPostalCode(),
                         placeOrderCommand.getCity(),
                         placeOrderCommand.getCountry(),
                         placeOrderCommand.getDeliveryDate(),
                         deliveryMinDelayDays,
                         new OrderQuantity(placeOrderCommand.getQuantityKg(),
                                           orderIncrementQuantityKg,
                                           orderMinQuantityKg,
                                           orderMaxQuantityKg),
                         new PriceEuro(pricePerKgEuro, placeOrderCommand.getQuantityKg())
        );
    }
}
