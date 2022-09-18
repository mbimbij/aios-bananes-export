package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.infra.in.CreateOrderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PlaceOrder {

    private final OrderFactory orderFactory;

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    public Order handle(CreateOrderRequestDto requestDto) throws OrderDeliveryTooEarlyException {
        Order order = orderFactory.createOrder(requestDto);
        try {
            order.validate();
        } catch (OrderDeliveryTooEarlyException e) {
            OrderFailedDeliveryDateTooEarlyEvent deliveryDateTooEarlyEvent = new OrderFailedDeliveryDateTooEarlyEvent(order);
            domainEventPublisher.send(deliveryDateTooEarlyEvent);
            log.error(e.getMessage(), e);
            throw e;
        }
        orderRepository.save(order);
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order);
        domainEventPublisher.send(orderCreatedEvent);
        log.info("request {} handled successfully", requestDto);
        return order;
    }

}
