package com.example.aiosbananesexport.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderCreatedEvent extends DomainEvent {
    Order order;

    public OrderCreatedEvent(String id, ZonedDateTime eventDateTime, Order order) {
        super(id, eventDateTime);
        this.order = order;
    }

    public static OrderCreatedEvent from(Order order) {
        return new OrderCreatedEvent(DomainEvent.generateId(),
                                     DomainEvent.getEventDateTime(),
                                     order);
    }
}