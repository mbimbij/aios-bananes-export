package com.example.aiosbananesexport.order.domain.entity;

import com.example.aiosbananesexport.recipient.domain.entity.Recipient;

import java.util.UUID;

public class OrderFactory {
    public OrderId generateOrderId() {
        return new OrderId(UUID.randomUUID().toString());
    }

    public Order createOrder(Recipient recipient, Order.Quantity quantity, Order.DeliveryDate deliveryDate, Price price) {
        return new Order(generateOrderId(),
                         recipient,
                         quantity,
                         deliveryDate,
                         price);
    }
}
