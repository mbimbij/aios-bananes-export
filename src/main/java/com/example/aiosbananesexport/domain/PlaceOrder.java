package com.example.aiosbananesexport.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PlaceOrder {

    private final OrderFactory orderFactory;

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    public Order handle(PlaceOrderCommand command) throws OrderDeliveryTooEarlyException, OrderQuantityNotInRangeException, OrderQuantityNotMultipleOfIncrementException {

        Order order = orderFactory.createOrder(command);
        try {
            order.validate();
        } catch (OrderDeliveryTooEarlyException e) {
            OrderFailedDeliveryDateTooEarlyEvent errorEvent = new OrderFailedDeliveryDateTooEarlyEvent(order.getId());
            domainEventPublisher.send(errorEvent);
            log.error(e.getMessage(), e);
            throw e;
        } catch (OrderQuantityNotInRangeException e) {
            OrderFailedQuantityNotInRangeEvent errorEvent = new OrderFailedQuantityNotInRangeEvent(order.getId(), order.getOrderQuantity());
            domainEventPublisher.send(errorEvent);
            log.error(e.getMessage(), e);
            throw e;
        } catch (OrderQuantityNotMultipleOfIncrementException e) {
            OrderFailedQuantityNotMultipleOfAllowedIncrementEvent errorEvent = new OrderFailedQuantityNotMultipleOfAllowedIncrementEvent(order.getId(), order.getOrderQuantity());
            domainEventPublisher.send(errorEvent);
            log.error(e.getMessage(), e);
            throw e;
        }

        orderRepository.save(order);
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order);
        domainEventPublisher.send(orderPlacedEvent);
        log.info("request {} handled successfully", command);
        return order;
    }

}
