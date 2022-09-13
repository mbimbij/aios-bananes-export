package com.example.aiosbananesexport.order.domain.usecase;


import com.example.aiosbananesexport.order.domain.entity.*;
import com.example.aiosbananesexport.recipient.domain.entity.Address;
import com.example.aiosbananesexport.recipient.domain.entity.Name;
import com.example.aiosbananesexport.recipient.domain.entity.Recipient;
import com.example.aiosbananesexport.recipient.domain.entity.RecipientRepository;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientNotFoundException;

public class PlaceOrder {
    private final OrderFactory orderFactory;
    private final OrderRepository orderRepository;
    private final RecipientRepository recipientRepository;
    private final PricePerKilogram pricePerKilogram;

    public PlaceOrder(OrderFactory orderFactory,
                      OrderRepository orderRepository,
                      RecipientRepository recipientRepository,
                      PricePerKilogram pricePerKilogram) {
        this.orderFactory = orderFactory;
        this.orderRepository = orderRepository;
        this.recipientRepository = recipientRepository;
        this.pricePerKilogram = pricePerKilogram;
    }

    public Order placeOrder(PlaceOrderCommand placeOrderCommand) {
        Name name = placeOrderCommand.getName();
        Address address = placeOrderCommand.getAddress();

        return recipientRepository.getByNameAndAddress(name, address)
                                  .map(recipient -> this.createOrder(recipient, placeOrderCommand))
                                  .orElseThrow(() -> new RecipientNotFoundException(name, address));
    }

    private Order createOrder(Recipient recipient, PlaceOrderCommand placeOrderCommand) {
        Price price = new Price(placeOrderCommand.getQuantity(), pricePerKilogram);
        Order order = this.orderFactory.createOrder(recipient,
                                                    placeOrderCommand.getQuantity(),
                                                    placeOrderCommand.getDeliveryDate(),
                                                    price);
        this.orderRepository.saveOrder(order);
        return order;
    }
}
