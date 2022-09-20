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
public class OrderPlacedEvent extends DomainEvent {
    private final Order order;

    public OrderPlacedEvent(Order order) {
        super(order.getId());
        this.order = order;
    }

    public OrderPlacedEvent(String id, ZonedDateTime eventDateTime, Order order) {
        super(id, order.getId(), eventDateTime);
        this.order = order;
    }
}
