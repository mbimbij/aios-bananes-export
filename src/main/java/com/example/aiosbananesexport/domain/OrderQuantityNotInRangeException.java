package com.example.aiosbananesexport.domain;

public class OrderQuantityNotInRangeException extends BusinessException {
    public OrderQuantityNotInRangeException(OrderQuantity orderQuantity) {
        super("quantity not in range: quantity:%d, min: %d, max:%s".formatted(orderQuantity.quantityKg(),
                                                                              orderQuantity.orderMinQuantityKg(),
                                                                              orderQuantity.orderMaxQuantityKg()));
    }
}
