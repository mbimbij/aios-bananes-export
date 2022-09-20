package com.example.aiosbananesexport.domain;

public class OrderDeliveryTooEarlyException extends BusinessException {
    public OrderDeliveryTooEarlyException(DeliveryDate deliveryDate) {
        super("delivery date too early: %s".formatted(deliveryDate));
    }
}
