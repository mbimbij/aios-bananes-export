package com.example.aiosbananesexport.infra.in;

import com.example.aiosbananesexport.domain.*;
import com.example.aiosbananesexport.utils.TimestampGenerator;
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
    public Mono<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto) throws OrderDeliveryTooEarlyException, OrderQuantityNotInRangeException {
        Order order = placeOrder.handle(requestDto);
        CreateOrderResponseDto responseDto = new CreateOrderResponseDto(order);
        return Mono.just(responseDto);
    }

    @ExceptionHandler({OrderDeliveryTooEarlyException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<BusinessErrorDto> handleOrderDeliveryTooEarlyException(OrderDeliveryTooEarlyException exception) {
        return handleBusinessExceptionInternal(exception);
    }

    @ExceptionHandler({OrderQuantityNotInRangeException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<BusinessErrorDto> handleQuantityNotInRangeException(OrderQuantityNotInRangeException exception) {
        return handleBusinessExceptionInternal(exception);
    }

    private Mono<BusinessErrorDto> handleBusinessExceptionInternal(BusinessException exception) {
        BusinessErrorDto businessErrorDto = new BusinessErrorDto(exception.getMessage(), exception, TimestampGenerator.now());
        return Mono.just(businessErrorDto);
    }
}
