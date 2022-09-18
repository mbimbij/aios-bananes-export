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
public class OrderFailedDeliveryDateTooEarlyEvent extends DomainEvent {
    public OrderFailedDeliveryDateTooEarlyEvent(Order order) {
        super(order);
    }

    public OrderFailedDeliveryDateTooEarlyEvent(String id, ZonedDateTime eventDateTime, Order order) {
        super(id, eventDateTime, order);
    }
}
