package com.example.aiosbananesexport.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderCreatedEvent extends DomainEvent {
    Order order;

    public OrderCreatedEvent(String id, Order order) {
        super(id);
        this.order = order;
    }

    public static OrderCreatedEvent from(Order order){
        return new OrderCreatedEvent(DomainEvent.generateId(), order);
    }
}
