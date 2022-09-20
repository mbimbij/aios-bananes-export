package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.common.SelfValidating;
import lombok.With;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@With
public record DeliveryDate(
        LocalDate orderPlacementDate,
        LocalDate deliveryDate,
        int deliveryMinDelayDays
) implements SelfValidating {
    private boolean deliveryDateTooEarly() {
        return deliveryDate.isBefore(LocalDate.now().plusDays(deliveryMinDelayDays));
    }

    @Override
    public void validate() throws OrderDeliveryTooEarlyException {
        if (deliveryDateTooEarly()) {
            throw new OrderDeliveryTooEarlyException(this);
        }
    }

    public String formatDeliveryDate(DateTimeFormatter dateFormatter) {
        return deliveryDate.format(dateFormatter);
    }
}
