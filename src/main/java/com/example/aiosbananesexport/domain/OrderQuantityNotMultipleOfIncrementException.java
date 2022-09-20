package com.example.aiosbananesexport.domain;

public class OrderQuantityNotMultipleOfIncrementException extends BusinessException {
    public OrderQuantityNotMultipleOfIncrementException(OrderQuantity orderQuantity) {
        super("quantity not a multiple of allowed increment: quantityKg:%d, allowedIncrement:%d".formatted(
                orderQuantity.quantityKg(),
                orderQuantity.orderIncrementQuantityKg()));
    }
}
