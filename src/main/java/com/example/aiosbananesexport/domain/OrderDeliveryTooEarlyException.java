package com.example.aiosbananesexport.domain;

public class OrderDeliveryTooEarlyException extends BusinessException {
    public OrderDeliveryTooEarlyException(Order order) {
        super("delivery date too early: %s".formatted(order.toString()));
    }
}
