package com.example.aiosbananesexport.order;

import com.example.aiosbananesexport.order.domain.*;
import com.example.aiosbananesexport.order.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.recipient.domain.*;
import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class OrderServiceShould {

    private OrderId orderId;
    private RecipientId recipientId;
    private PricePerKilogram pricePerKilogram;
    private InMemoryOrderRepository orderRepository;
    private Name name;
    private Address address;
    private Recipient recipient;
    private RecipientRepository recipientRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderId = new OrderId("id");
        recipientId = new RecipientId("id");
        pricePerKilogram = new PricePerKilogram(2.5);

        orderRepository = spy(new InMemoryOrderRepository());
        doReturn(orderId)
                .when(orderRepository)
                .generateOrderId();

        name = new Name(new Name.FirstName("firstName"),
                             new Name.LastName("lastName"));
        address = new Address(new Address.Street("address"),
                                      new Address.PostalCode(75019),
                                      new Address.City("Paris"),
                                      new Address.Country("France"));
        recipient = new Recipient(recipientId, name, address);

        recipientRepository = new InMemoryRecipientRepository();
        recipientRepository.saveRecipient(recipient);

        orderService = new OrderService(orderRepository, recipientRepository, pricePerKilogram);
    }

    @Test
    void create_an_order__in_nominal_case() {
        // GIVEN
        Order.Quantity quantity = new Order.Quantity(25);
        Order.DeliveryDate deliveryDate = new Order.DeliveryDate(LocalDate.now().plusWeeks(1));
        Price expectedPrice = new Price(62.5);
        Order expectedOrder = new Order(orderId, recipient, quantity, deliveryDate, expectedPrice);
        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(name, address, quantity, deliveryDate);

        // WHEN
        Order order = orderService.placeOrder(placeOrderCommand);

        // THEN
        Optional<Order> actualOrder = orderService.getOrderById(order.getOrderId());
        assertThat(actualOrder).hasValue(expectedOrder);
    }
}
