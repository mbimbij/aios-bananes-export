package com.example.aiosbananesexport.domain;

public class OrderDeliveryTooEarlyException extends Exception {
    public OrderDeliveryTooEarlyException(Order order) {
        super("delivery date too early: %s".formatted( order.toString()));
    }
}
