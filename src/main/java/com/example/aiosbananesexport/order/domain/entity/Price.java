package com.example.aiosbananesexport.order.domain.entity;

import lombok.Value;

@Value
public class Price {
    double amount;

    public Price(Order.Quantity quantity, PricePerKilogram pricePerKilogram) {
        this.amount = quantity.getValue() * pricePerKilogram.getValue();
    }

    public Price(double amount) {
        this.amount = amount;
    }
}
