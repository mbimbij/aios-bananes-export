package com.example.aiosbananesexport.order.domain;

import com.example.aiosbananesexport.common.ValueObject;
import com.example.aiosbananesexport.recipient.domain.Recipient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@ToString
public class Order {
    private final OrderId orderId;
    private final Recipient recipient;
    private final Quantity quantity;
    private final DeliveryDate deliveryDate;

    private final Price price;

    public Order(OrderId orderId, Recipient recipient, Quantity quantity, DeliveryDate deliveryDate, Price price) {
        this.orderId = orderId;
        this.recipient = recipient;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
        this.price = price;
    }

    public static class Quantity extends ValueObject<Integer> {
        public Quantity(Integer value) {
            super(value);
        }
    }

    public static class DeliveryDate extends ValueObject<LocalDate> {
        public DeliveryDate(LocalDate deliveryDate) {
            super(deliveryDate);
        }
    }

}
