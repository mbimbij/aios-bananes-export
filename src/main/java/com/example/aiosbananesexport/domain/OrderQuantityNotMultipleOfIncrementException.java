package com.example.aiosbananesexport.domain;

public class OrderQuantityNotMultipleOfIncrementException extends BusinessException {
    public OrderQuantityNotMultipleOfIncrementException(Order order) {
        super("quantity not a multiple of allowed increment: %d".formatted(order.quantityKg));
    }
}
