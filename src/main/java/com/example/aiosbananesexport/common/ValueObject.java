package com.example.aiosbananesexport.common;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Need to use {@code @EqualsAndHashCode(callSuper = true)} on classes inheriting from {@code ValueObject} to avoid different subclasses with that value to be equal
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
abstract public class ValueObject<T> {
    private T value;
}
