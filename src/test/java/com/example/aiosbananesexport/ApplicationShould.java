package com.example.aiosbananesexport;

import com.example.aiosbananesexport.domain.*;
import com.example.aiosbananesexport.infra.in.BusinessErrorDto;
import com.example.aiosbananesexport.infra.in.DateTimeFormat;
import com.example.aiosbananesexport.infra.in.PlaceOrderRequestDto;
import com.example.aiosbananesexport.infra.in.PlaceOrderResponseDto;
import com.example.aiosbananesexport.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.infra.out.MockDomainEventPublisher;
import com.example.aiosbananesexport.utils.IdGenerator;
import com.example.aiosbananesexport.utils.TimestampGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ApplicationTestConfiguration.class
)
class ApplicationShould {

    private final String fixedId = "fixedId";
    private final ZonedDateTime fixedTimestamp = ZonedDateTime.now();
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private InMemoryOrderRepository orderRepository;

    @Autowired
    private MockDomainEventPublisher domainEventPublisher;

    @Autowired
    private OrderConfigurationProperties orderConfigurationProperties;

    @BeforeEach
    void setUp() {
        TimestampGenerator.useFixedClockAt(fixedTimestamp);
        IdGenerator.useFixedId(fixedId);

        domainEventPublisher.clear();
        orderRepository.clear();
    }

    @Test
    void create_an_order__in_nominal_case() {
        // GIVEN
        PlaceOrderRequestDto requestDto = happyCaseOrderRequestDto();
        PlaceOrderResponseDto expectedResponseDto = happyCaseOrderResponseDto();
        Order expectedOrder = happyCaseOrder();
        OrderCreatedEvent expectedEvent = new OrderCreatedEvent(fixedId, fixedTimestamp, expectedOrder);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is correct
        responseSpec.expectStatus()
                    .isCreated()
                    .expectBody(PlaceOrderResponseDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(expectedResponseDto));

        // AND the order is saved to the repository
        assertThat(orderRepository.getOrders()).anySatisfy(order -> assertThat(order).usingRecursiveComparison().isEqualTo(expectedOrder));

        // AND an "OrderCreatedEvent" is published
        List<DomainEvent> domainEvents = domainEventPublisher.getDomainEvents();
        assertThat(domainEvents).anySatisfy(domainEvent -> assertThat(domainEvent).usingRecursiveComparison().isEqualTo(expectedEvent));
    }

    @Test
    void return_an_error__when_delivery_date_too_early() {
        // GIVEN
        LocalDate earlyDeliveryDate = LocalDate.now().plusWeeks(1).minusDays(1);
        String earlyDeliveryDateString = earlyDeliveryDate.format(DateTimeFormat.DATE_FORMATTER);

        PlaceOrderRequestDto requestDto = new PlaceOrderRequestDto("firstName", "lastName", "address", 75019, "paris", "france", earlyDeliveryDateString, 25);
        Order expectedOrderInError = happyCaseOrder().withDeliveryDate(earlyDeliveryDate);
        OrderDeliveryTooEarlyException exception = new OrderDeliveryTooEarlyException(expectedOrderInError);
        BusinessErrorDto BusinessErrorDto = new BusinessErrorDto(exception.getMessage(), exception, fixedTimestamp);
        OrderFailedDeliveryDateTooEarlyEvent expectedEvent = new OrderFailedDeliveryDateTooEarlyEvent(fixedId, fixedTimestamp, expectedOrderInError);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is http 409 with error response
        responseSpec.expectStatus()
                    .isEqualTo(409)
                    .expectBody(BusinessErrorDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(BusinessErrorDto));

        // AND an error event is published
        assertThat(domainEventPublisher.getDomainEvents())
                .filteredOn(event -> event instanceof OrderFailedDeliveryDateTooEarlyEvent)
                .hasSize(1)
                .allSatisfy(domainEvent -> assertThat(domainEvent).usingRecursiveComparison().isEqualTo(expectedEvent));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10_001})
    void return_an_error__when_quantity_not_in_allowed_range(int wrongQuantityKg) {
        // GIVEN
        PlaceOrderRequestDto requestDto = happyCaseOrderRequestDto().withQuantityKg(wrongQuantityKg);
        OrderQuantity wrongOrderQuantity = happyCaseOrderQuantity().withQuantityKg(wrongQuantityKg);
        OrderQuantityNotInRangeException expectedException = new OrderQuantityNotInRangeException(wrongOrderQuantity);
        BusinessErrorDto BusinessErrorDto = new BusinessErrorDto(expectedException.getMessage(), expectedException, fixedTimestamp);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is http 409 with error response
        responseSpec.expectStatus()
                    .isEqualTo(409)
                    .expectBody(BusinessErrorDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(BusinessErrorDto));
    }

    @Test
    void return_an_error__when_quantity_not_multiple_of_allowed_hincrement() {
        // GIVEN
        int wrongQuantityKg = 30;
        PlaceOrderRequestDto requestDto = happyCaseOrderRequestDto().withQuantityKg(wrongQuantityKg);
        OrderQuantity wrongOrderQuantity = happyCaseOrderQuantity().withQuantityKg(wrongQuantityKg);
        OrderQuantityNotMultipleOfIncrementException expectedException = new OrderQuantityNotMultipleOfIncrementException(wrongOrderQuantity);
        BusinessErrorDto BusinessErrorDto = new BusinessErrorDto(expectedException.getMessage(), expectedException, fixedTimestamp);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is http 409 with error response
        responseSpec.expectStatus()
                    .isEqualTo(409)
                    .expectBody(BusinessErrorDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(BusinessErrorDto));
    }

    private PlaceOrderResponseDto happyCaseOrderResponseDto() {
        return new PlaceOrderResponseDto(fixedId,
                                         "firstName",
                                         "lastName",
                                         "address",
                                         75019,
                                         "paris",
                                         "france",
                                         LocalDate.now().plusWeeks(1).format(DateTimeFormat.DATE_FORMATTER),
                                         25,
                                         "62.50");
    }

    private PlaceOrderRequestDto happyCaseOrderRequestDto() {
        return new PlaceOrderRequestDto("firstName",
                                        "lastName",
                                        "address",
                                        75019,
                                        "paris",
                                        "france",
                                        LocalDate.now().plusWeeks(1).format(DateTimeFormat.DATE_FORMATTER),
                                        25);
    }

    private Order happyCaseOrder() {
        PriceEuro expectedPrice = new PriceEuro(62.5);
        OrderQuantity orderQuantity = happyCaseOrderQuantity();
        return new Order(fixedId,
                         "firstName",
                         "lastName",
                         "address",
                         75019,
                         "paris",
                         "france",
                         LocalDate.now().plusWeeks(1),
                         orderConfigurationProperties.getDeliveryMinDelayDays(),
                         orderQuantity,
                         expectedPrice);
    }

    private OrderQuantity happyCaseOrderQuantity() {
        return new OrderQuantity(25,
                                 orderConfigurationProperties.getIncrementQuantityKg(),
                                 orderConfigurationProperties.getMinQuantityKg(),
                                 orderConfigurationProperties.getMaxQuantityKg());
    }
}
