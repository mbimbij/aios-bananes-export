package com.example.aiosbananesexport.order.infra.out;

import com.example.aiosbananesexport.order.domain.Order;
import com.example.aiosbananesexport.order.domain.OrderId;
import com.example.aiosbananesexport.order.domain.OrderRepository;
import com.example.aiosbananesexport.order.domain.Price;
import com.example.aiosbananesexport.recipient.domain.Recipient;

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
