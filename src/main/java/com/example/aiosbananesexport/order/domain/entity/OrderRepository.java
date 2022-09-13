package com.example.aiosbananesexport.order.domain.entity;

import java.util.Optional;

public interface OrderRepository {
    void saveOrder(Order order);

    Optional<Order> getOrderById(OrderId orderId);
}
