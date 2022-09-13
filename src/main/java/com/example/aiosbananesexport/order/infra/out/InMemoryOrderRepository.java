package com.example.aiosbananesexport.order.infra.out;

import com.example.aiosbananesexport.order.domain.entity.Order;
import com.example.aiosbananesexport.order.domain.entity.OrderId;
import com.example.aiosbananesexport.order.domain.entity.OrderRepository;

import java.util.*;

public class InMemoryOrderRepository implements OrderRepository {
    Map<OrderId, Order> orders = new HashMap<>();

    @Override
    public void saveOrder(Order order) {
        orders.put(order.getOrderId(), order);
    }

    @Override
    public Optional<Order> getOrderById(OrderId orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public Collection<Order>getOrders() {
        return orders.values();
    }
}
