package com.example.aiosbananesexport.order;

import com.example.aiosbananesexport.order.domain.*;
import com.example.aiosbananesexport.order.domain.exception.DeliveryDateTooEarlyException;
import com.example.aiosbananesexport.order.domain.exception.OrderQuantityException;
import com.example.aiosbananesexport.order.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.recipient.domain.*;
import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class OrderServiceShould {

    private OrderId orderId;
    private RecipientId recipientId;
    private PricePerKilogram pricePerKilogram;
    private InMemoryOrderRepository orderRepository;
    private OrderFactory orderFactory;
    private Name name;
    private Address address;
    private Recipient recipient;
    private RecipientRepository recipientRepository;
    private OrderQuantityConfig orderQuantityConfig;
    private OrderService orderService;
    private LocalDate orderPlacementDate;

    @BeforeEach
    void setUp() {
        orderId = new OrderId("id");
        recipientId = new RecipientId("id");
        pricePerKilogram = new PricePerKilogram(2.5);
        orderPlacementDate = LocalDate.now();

        orderRepository = new InMemoryOrderRepository();
        orderFactory = spy(new OrderFactory());
        doReturn(orderId)
                .when(orderFactory)
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

        orderQuantityConfig = new OrderQuantityConfig(0, 10_000, 25);
        orderService = new OrderService(orderFactory, orderRepository, recipientRepository, pricePerKilogram, orderQuantityConfig);
    }

    @Test
    void create_an_order__in_nominal_case() {
        // GIVEN
        Order.Quantity quantity = new Order.Quantity(25, orderQuantityConfig);
        Order.DeliveryDate deliveryDate = new Order.DeliveryDate(orderPlacementDate, LocalDate.now().plusWeeks(1));
        Price expectedPrice = new Price(62.5);
        Order expectedOrder = new Order(orderId, recipient, quantity, deliveryDate, expectedPrice);
        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(name, address, quantity, deliveryDate);

        // WHEN
        Order order = orderService.placeOrder(placeOrderCommand);

        // THEN
        Optional<Order> actualOrder = orderService.getOrderById(order.getOrderId());
        assertThat(actualOrder).hasValue(expectedOrder);
    }

    @Test
    void throw_an_exception__when_delivery_date_less_than_a_week_after_order_placement() {
        // GIVEN
        Order.Quantity quantity = new Order.Quantity(25, orderQuantityConfig);
        Order.DeliveryDate deliveryDate = new Order.DeliveryDate(orderPlacementDate, LocalDate.now().plusDays(1));
        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(name, address, quantity, deliveryDate);

        // WHEN
        ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.placeOrder(placeOrderCommand);

        // THEN
        assertThatThrownBy(throwingCallable).isInstanceOf(DeliveryDateTooEarlyException.class);
        assertThat(orderRepository.getOrders()).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {
            -1,
            30,
            10001})
    void throw_an_exception__when_wrong_delivery_quantity(int quantityInt) {
        // GIVEN
        Order.Quantity quantity = new Order.Quantity(quantityInt, orderQuantityConfig);
        Order.DeliveryDate deliveryDate = new Order.DeliveryDate(orderPlacementDate, LocalDate.now().plusWeeks(1));
        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(name, address, quantity, deliveryDate);

        // WHEN
        ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.placeOrder(placeOrderCommand);

        // THEN
        assertThatThrownBy(throwingCallable).isInstanceOf(OrderQuantityException.class);
        assertThat(orderRepository.getOrders()).isEmpty();
    }
}
