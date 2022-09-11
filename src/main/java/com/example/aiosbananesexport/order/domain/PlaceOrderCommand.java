package com.example.aiosbananesexport.order.domain;

import com.example.aiosbananesexport.recipient.domain.Address;
import com.example.aiosbananesexport.recipient.domain.Name;
import lombok.Value;

@Value
public class PlaceOrderCommand {
    Name name;
    Address address;
    Order.Quantity quantity;
    Order.DeliveryDate deliveryDate;
}
