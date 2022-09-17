package com.example.aiosbananesexport.infra.out;

import com.example.aiosbananesexport.domain.Order;
import com.example.aiosbananesexport.domain.OrderRepository;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InMemoryOrderRepository implements OrderRepository {
    List<Order> orders = new ArrayList<>();
    @Override
    public void save(Order order) {
        orders.add(order);
    }
}
