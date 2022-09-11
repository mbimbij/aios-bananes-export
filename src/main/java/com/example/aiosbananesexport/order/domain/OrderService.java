package com.example.aiosbananesexport.order.domain;


import com.example.aiosbananesexport.recipient.domain.Address;
import com.example.aiosbananesexport.recipient.domain.Name;
import com.example.aiosbananesexport.recipient.domain.RecipientRepository;
import com.example.aiosbananesexport.recipient.exception.RecipientNotFoundException;

import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;
    private final RecipientRepository recipientRepository;
    private final PricePerKilogram pricePerKilogram;

    public OrderService(OrderRepository orderRepository, RecipientRepository recipientRepository, PricePerKilogram pricePerKilogram) {
        this.orderRepository = orderRepository;
        this.recipientRepository = recipientRepository;
        this.pricePerKilogram = pricePerKilogram;
    }

    public Order placeOrder(PlaceOrderCommand placeOrderCommand) {
        Name name = placeOrderCommand.getName();
        Address address = placeOrderCommand.getAddress();

        return recipientRepository.getByNameAndAddress(name, address)
                                  .map(recipient -> {
                                      Price price = new Price(placeOrderCommand.getQuantity(), pricePerKilogram);
                                      Order order = this.orderRepository.createOrder(recipient,
                                                                                     placeOrderCommand.getQuantity(),
                                                                                     placeOrderCommand.getDeliveryDate(),
                                                                                     price);
                                      this.orderRepository.saveOrder(order);
                                      return order;
                                  })
                                  .orElseThrow(() -> new RecipientNotFoundException(name, address));
    }

    public Optional<Order> getOrderById(OrderId orderId) {
        return orderRepository.getOrderById(orderId);
    }
}
