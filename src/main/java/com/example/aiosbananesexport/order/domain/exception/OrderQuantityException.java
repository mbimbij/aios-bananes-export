package com.example.aiosbananesexport.order.domain.exception;

import com.example.aiosbananesexport.common.DomainException;
import com.example.aiosbananesexport.order.domain.entity.OrderQuantityConfig;

public class OrderQuantityException extends DomainException {

    public OrderQuantityException(OrderQuantityConfig orderQuantityConfig, Integer orderedQuantity) {
        super("Bad delivery quantity. Must be between %d and %d and a multiple of %d, but was %d."
                      .formatted(orderQuantityConfig.getMinOrderQuantityKg(),
                                 orderQuantityConfig.getMaxOrderQuantityKg(),
                                 orderQuantityConfig.getIncrementOrderQuantityKg(),
                                 orderedQuantity));
    }
}
