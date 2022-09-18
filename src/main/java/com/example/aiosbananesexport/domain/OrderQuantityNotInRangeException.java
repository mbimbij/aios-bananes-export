package com.example.aiosbananesexport.domain;

public class OrderQuantityNotInRangeException extends BusinessException {
    public OrderQuantityNotInRangeException(Order order) {
        super("quantity not in range: %d".formatted(order.quantityKg));
    }
}
