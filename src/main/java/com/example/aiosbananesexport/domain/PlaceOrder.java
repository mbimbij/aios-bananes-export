package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.infra.in.CreateOrderRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceOrder {

    private final DomainEventPublisher domainEventPublisher;
    private final OrderFactory orderFactory;

    public Order placeOrder(CreateOrderRequestDto requestDto) {
        Order order = orderFactory.createOrder(requestDto);
        domainEventPublisher.send(new OrderCreatedEvent("anyOrderCreatedEventid", order));
        return order;
    }

}
