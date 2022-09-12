package com.example.aiosbananesexport.order.domain;

import com.example.aiosbananesexport.recipient.domain.Recipient;

import java.util.Optional;

public interface OrderRepository {
    Order createOrder(Recipient recipient, Order.Quantity quantity, Order.DeliveryDate deliveryDate, Price price);

    void saveOrder(Order order);

    Optional<Order> getOrderById(OrderId orderId);
}
