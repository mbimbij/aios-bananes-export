package com.example.aiosbananesexport.order.domain.usecase;

import com.example.aiosbananesexport.order.domain.entity.Order;
import com.example.aiosbananesexport.recipient.domain.entity.Address;
import com.example.aiosbananesexport.recipient.domain.entity.Name;
import lombok.Value;

@Value
public class PlaceOrderCommand {
    Name name;
    Address address;
    Order.Quantity quantity;
    Order.DeliveryDate deliveryDate;
}
