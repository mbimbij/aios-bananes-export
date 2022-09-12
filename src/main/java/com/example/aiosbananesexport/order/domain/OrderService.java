package com.example.aiosbananesexport.order.domain;


import com.example.aiosbananesexport.recipient.domain.Address;
import com.example.aiosbananesexport.recipient.domain.Name;
import com.example.aiosbananesexport.recipient.domain.Recipient;
import com.example.aiosbananesexport.recipient.domain.RecipientRepository;
import com.example.aiosbananesexport.recipient.exception.RecipientNotFoundException;

import java.util.Optional;

public class OrderService {
    private final OrderFactory orderFactory;
    private final OrderRepository orderRepository;
    private final RecipientRepository recipientRepository;
    private final PricePerKilogram pricePerKilogram;
    private final OrderQuantityConfig orderQuantityConfig;

    public OrderService(OrderFactory orderFactory,
                        OrderRepository orderRepository,
                        RecipientRepository recipientRepository,
                        PricePerKilogram pricePerKilogram,
                        OrderQuantityConfig orderQuantityConfig) {
        this.orderFactory = orderFactory;
        this.orderRepository = orderRepository;
        this.recipientRepository = recipientRepository;
        this.pricePerKilogram = pricePerKilogram;
        this.orderQuantityConfig = orderQuantityConfig;
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

    public Optional<Order> getOrderById(OrderId orderId) {
        return orderRepository.getOrderById(orderId);
    }
}
