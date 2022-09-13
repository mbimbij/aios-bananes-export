package com.example.aiosbananesexport.recipient.domain.entity;

import com.example.aiosbananesexport.common.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
public class Name {
    FirstName firstName;
    LastName lastName;

    @EqualsAndHashCode(callSuper = true)
    public static class FirstName extends ValueObject<String> {
        public FirstName(String value) {
            super(value);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static class LastName extends ValueObject<String> {
        public LastName(String value) {
            super(value);
        }
    }
}
