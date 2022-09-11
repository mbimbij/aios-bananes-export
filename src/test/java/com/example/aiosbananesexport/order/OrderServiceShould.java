package com.example.aiosbananesexport.order;

import com.example.aiosbananesexport.order.domain.*;
import com.example.aiosbananesexport.order.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.recipient.domain.*;
import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class OrderServiceShould {
    @Test
    void create_an_order__in_nominal_case() {
        // GIVEN
        InMemoryOrderRepository orderRepository = spy(new InMemoryOrderRepository());
        OrderId orderId = new OrderId("id");
        doReturn(orderId)
                .when(orderRepository)
                .generateOrderId();

        Name name = new Name(new Name.FirstName("firstName"),
                             new Name.LastName("lastName"));

        Address address = new Address(new Address.Street("address"),
                                      new Address.PostalCode(75019),
                                      new Address.City("Paris"),
                                      new Address.Country("France"));
        RecipientId recipientId = new RecipientId("id");
        Recipient recipient = new Recipient(recipientId, name, address);

        RecipientRepository recipientRepository = new InMemoryRecipientRepository();
        recipientRepository.saveRecipient(recipient);

        PricePerKilogram pricePerKilogram = new PricePerKilogram(2.5);
        OrderService orderService = new OrderService(orderRepository, recipientRepository, pricePerKilogram);


        Order.Quantity quantity = new Order.Quantity(25);
        Order.DeliveryDate deliveryDate = new Order.DeliveryDate(LocalDate.now().plusWeeks(1));
        Price expectedPrice = new Price(62.5);

        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(name, address, quantity, deliveryDate);

        final Price price = new Price(quantity, pricePerKilogram);
        Order expectedOrder = new Order(orderId, recipient, quantity, deliveryDate, expectedPrice);

        // WHEN
        Order order = orderService.placeOrder(placeOrderCommand);

        // THEN
        Optional<Order> actualOrder = orderService.getOrderById(order.getOrderId());
        assertThat(actualOrder).hasValue(expectedOrder);
    }
}
