package com.example.aiosbananesexport.common;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
abstract public class Id<T> extends ValueObject<T> {
    public Id(T value) {
        super(value);
    }
}
