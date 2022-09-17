package com.example.aiosbananesexport.infra.in;

import com.example.aiosbananesexport.domain.Order;
import com.example.aiosbananesexport.domain.OrderDeliveryTooEarlyException;
import com.example.aiosbananesexport.domain.PlaceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@RestController
public class ApplicationRestController {

    @Autowired
    private PlaceOrder placeOrder;

    @PostMapping(value = "/order")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto) throws OrderDeliveryTooEarlyException {
        Order order = placeOrder.handle(requestDto);
        CreateOrderResponseDto responseDto = new CreateOrderResponseDto(order);
        return Mono.just(responseDto);
    }

    @ExceptionHandler({OrderDeliveryTooEarlyException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<BusinessErrorDto> handleOrderDeliveryTooEarlyException(OrderDeliveryTooEarlyException exception) {
        BusinessErrorDto businessErrorDto = new BusinessErrorDto(exception.getMessage(), exception, currentTimestamp());
        return Mono.just(businessErrorDto);
    }

    public ZonedDateTime currentTimestamp() {
        return ZonedDateTime.now();
    }

}
