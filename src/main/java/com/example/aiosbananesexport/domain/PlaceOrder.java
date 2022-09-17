package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.infra.in.CreateOrderRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceOrder {

    private final OrderFactory orderFactory;

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    public Order placeOrder(CreateOrderRequestDto requestDto) {
        Order order = orderFactory.createOrder(requestDto);
        orderRepository.save(order);
        domainEventPublisher.send(OrderCreatedEvent.from(order));
        return order;
    }

}
