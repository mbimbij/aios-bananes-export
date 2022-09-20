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
public class OrderFailedQuantityNotMultipleOfAllowedIncrementEvent extends DomainEvent {
    private final OrderQuantity orderQuantity;

    public OrderFailedQuantityNotMultipleOfAllowedIncrementEvent(String orderId, OrderQuantity orderQuantity) {
        super(orderId);
        this.orderQuantity = orderQuantity;
    }

    public OrderFailedQuantityNotMultipleOfAllowedIncrementEvent(String id, String orderId, ZonedDateTime eventDateTime, OrderQuantity orderQuantity) {
        super(id, orderId, eventDateTime);
        this.orderQuantity = orderQuantity;
    }
}
