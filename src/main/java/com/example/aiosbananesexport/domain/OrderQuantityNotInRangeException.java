package com.example.aiosbananesexport.domain;

public class OrderQuantityNotInRangeException extends BusinessException {
    public OrderQuantityNotInRangeException(OrderQuantity orderQuantity) {
        super("quantity not in range: %d".formatted(orderQuantity.quantityKg()));
    }
}
