package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.common.SelfValidating;
import lombok.With;

@With
public record OrderQuantity(
        int quantityKg,
        int orderIncrementQuantityKg,
        int orderMinQuantityKg,
        int orderMaxQuantityKg
) implements SelfValidating {
    @Override
    public void validate() throws OrderQuantityNotInRangeException, OrderQuantityNotMultipleOfIncrementException {
        if (!quantityInAllowedRange()) {
            throw new OrderQuantityNotInRangeException(this);
        }
        if (!quantityMultipleOfAllowedIncrement()) {
            throw new OrderQuantityNotMultipleOfIncrementException(this);
        }
    }

    private boolean quantityInAllowedRange() {
        return quantityKg > orderMinQuantityKg && quantityKg <= orderMaxQuantityKg;
    }

    private boolean quantityMultipleOfAllowedIncrement() {
        return quantityKg % orderIncrementQuantityKg==0;
    }
}
