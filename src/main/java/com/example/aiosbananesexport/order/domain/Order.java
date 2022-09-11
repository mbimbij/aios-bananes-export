package com.example.aiosbananesexport.order.domain;

import com.example.aiosbananesexport.common.DateFormat;
import com.example.aiosbananesexport.common.ValueObject;
import com.example.aiosbananesexport.order.domain.exception.DeliveryDateTooEarlyException;
import com.example.aiosbananesexport.recipient.domain.Recipient;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

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

    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class DeliveryDate {
        LocalDate orderPlacementDate;
        LocalDate deliveryDate;
        LocalDate minDeliveryDate;

        public DeliveryDate(LocalDate orderPlacementDate, LocalDate deliveryDate) {
            this.orderPlacementDate = orderPlacementDate;
            this.deliveryDate = deliveryDate;
            this.minDeliveryDate = orderPlacementDate.plusWeeks(1);
        }

        public void validate() {
            if (deliveryDate.isBefore(minDeliveryDate)) {
                throw new DeliveryDateTooEarlyException(this);
            }
        }

        public String getFormattedOrderPlacementDate() {
            return orderPlacementDate.format(DateFormat.dateTimeFormatter);
        }

        public String getFormattedDeliveryDate() {
            return deliveryDate.format(DateFormat.dateTimeFormatter);
        }

        public String getFormattedMinDeliveryDate() {
            return minDeliveryDate.format(DateFormat.dateTimeFormatter);
        }
    }

    public void validate() {
        deliveryDate.validate();
    }
}
