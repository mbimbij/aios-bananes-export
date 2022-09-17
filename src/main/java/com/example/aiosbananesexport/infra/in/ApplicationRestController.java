package com.example.aiosbananesexport.infra.in;

import com.example.aiosbananesexport.domain.Order;
import com.example.aiosbananesexport.domain.OrderDeliveryTooEarlyException;
import com.example.aiosbananesexport.domain.PlaceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class ApplicationRestController {

    @Autowired
    private PlaceOrder placeOrder;

    @PostMapping(value = "/order")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto) throws OrderDeliveryTooEarlyException {
        Order order = placeOrder.placeOrder(requestDto);
        CreateOrderResponseDto responseDto = new CreateOrderResponseDto(order.getId(),
                                                                        order.getFirstName(),
                                                                        order.getLastName(),
                                                                        order.getAddress(),
                                                                        order.getPostalCode(),
                                                                        order.getCity(),
                                                                        order.getCountry(),
                                                                        order.getDeliveryDate().format(DateTimeFormat.DATE_FORMATTER),
                                                                        order.getQuantityKg(),
                                                                        String.format("%.2f", order.getPrice()));
        return Mono.just(responseDto);
    }

    @ExceptionHandler({OrderDeliveryTooEarlyException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<BusinessErrorDto> handleOrderDeliveryTooEarlyException(OrderDeliveryTooEarlyException exception) {
        BusinessErrorDto businessErrorDto = new BusinessErrorDto(HttpStatus.CONFLICT, exception.getMessage(), exception);
        return Mono.just(businessErrorDto);
    }

}
