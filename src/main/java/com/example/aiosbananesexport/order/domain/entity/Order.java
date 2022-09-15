package com.example.aiosbananesexport.order.domain.entity;

import com.example.aiosbananesexport.common.DateFormat;
import com.example.aiosbananesexport.common.SelfValidating;
import com.example.aiosbananesexport.common.ValueObject;
import com.example.aiosbananesexport.order.domain.exception.DeliveryDateTooEarlyException;
import com.example.aiosbananesexport.order.domain.exception.OrderQuantityException;
import com.example.aiosbananesexport.recipient.domain.entity.Recipient;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@ToString
public class Order implements SelfValidating {
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

    @Override
    public void validate() {
        deliveryDate.validate();
        quantity.validate();
    }

    public static class Quantity extends ValueObject<Integer> implements SelfValidating {
        private final OrderQuantityConfig orderQuantityConfig;

        public Quantity(Integer value, OrderQuantityConfig orderQuantityConfig) {

            super(value);
            this.orderQuantityConfig = orderQuantityConfig;
        }

        @Override
        public void validate() {
            if (!inAllowedRange() || !multipleOfAllowedIncrement()) {
                throw new OrderQuantityException(orderQuantityConfig, getValue());
            }
        }

        private boolean multipleOfAllowedIncrement() {
            return getValue() % orderQuantityConfig.getIncrementOrderQuantityKg()==0;
        }

        private boolean inAllowedRange() {
            return getValue() > orderQuantityConfig.getMinOrderQuantityKg() && getValue() < orderQuantityConfig.getMaxOrderQuantityKg();
        }
    }

    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class DeliveryDate implements SelfValidating {
        LocalDate orderPlacementDate;
        LocalDate deliveryDate;
        LocalDate minDeliveryDate;

        public DeliveryDate(LocalDate orderPlacementDate, LocalDate deliveryDate) {
            this.orderPlacementDate = orderPlacementDate;
            this.deliveryDate = deliveryDate;
            this.minDeliveryDate = orderPlacementDate.plusWeeks(1);
        }

        @Override
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
}
