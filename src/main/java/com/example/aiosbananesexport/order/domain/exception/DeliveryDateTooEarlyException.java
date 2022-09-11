package com.example.aiosbananesexport.order.domain.exception;

import com.example.aiosbananesexport.common.DateFormat;
import com.example.aiosbananesexport.common.DomainException;
import com.example.aiosbananesexport.order.domain.Order;

public class DeliveryDateTooEarlyException extends DomainException {

    public DeliveryDateTooEarlyException(Order.DeliveryDate deliveryDate) {
        super("the delivery date asked is too early. (date format:: %s) delivery date: %s, order placement date: %s, min delivery date: %s"
                      .formatted(DateFormat.dateFormatPatternString,
                                 deliveryDate.getFormattedDeliveryDate(),
                                 deliveryDate.getFormattedOrderPlacementDate(),
                                 deliveryDate.getFormattedMinDeliveryDate()));
    }
}
