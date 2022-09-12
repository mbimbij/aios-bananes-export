package com.example.aiosbananesexport.order.domain;

import com.example.aiosbananesexport.recipient.domain.Recipient;

import java.util.UUID;

public class OrderFactory {
    public OrderId generateOrderId() {
        return new OrderId(UUID.randomUUID().toString());
    }

    public Order createOrder(Recipient recipient, Order.Quantity quantity, Order.DeliveryDate deliveryDate, Price price) {
        Order order = new Order(generateOrderId(),
                                recipient,
                                quantity,
                                deliveryDate,
                                price);
        order.validate();
        return order;
    }
}
