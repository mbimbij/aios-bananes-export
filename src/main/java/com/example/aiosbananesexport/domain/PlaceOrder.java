package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.infra.in.PlaceOrderRequestDto;
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
            OrderFailedDeliveryDateTooEarlyEvent deliveryDateTooEarlyEvent = new OrderFailedDeliveryDateTooEarlyEvent(order);
            domainEventPublisher.send(deliveryDateTooEarlyEvent);
            log.error(e.getMessage(), e);
            throw e;
        } catch (OrderQuantityNotInRangeException e) {
            OrderFailedQuantityNotInRangeEvent deliveryDateTooEarlyEvent = new OrderFailedQuantityNotInRangeEvent(order);
            domainEventPublisher.send(deliveryDateTooEarlyEvent);
            log.error(e.getMessage(), e);
            throw e;
        } catch (OrderQuantityNotMultipleOfIncrementException e) {
            OrderFailedQuantityNotInRangeEvent deliveryDateTooEarlyEvent = new OrderFailedQuantityNotInRangeEvent(order);
            domainEventPublisher.send(deliveryDateTooEarlyEvent);
            log.error(e.getMessage(), e);
            throw e;
        }

        orderRepository.save(order);
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order);
        domainEventPublisher.send(orderCreatedEvent);
        log.info("request {} handled successfully", command);
        return order;
    }

}
