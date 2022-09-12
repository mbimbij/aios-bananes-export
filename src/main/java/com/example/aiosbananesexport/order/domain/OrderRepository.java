package com.example.aiosbananesexport.order.domain;

import com.example.aiosbananesexport.recipient.domain.Recipient;

import java.util.Optional;

public interface OrderRepository {
    void saveOrder(Order order);

    Optional<Order> getOrderById(OrderId orderId);
}
