package com.example.aiosbananesexport.recipient.domain.entity;

import com.example.aiosbananesexport.common.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
public class Address {
    Street street;
    PostalCode postalCode;
    City city;
    Country country;

    @EqualsAndHashCode(callSuper = true)
    public static class City extends ValueObject<String> {
        public City(String value) {
            super(value);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static class Country extends ValueObject<String> {
        public Country(String value) {
            super(value);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static class Street extends ValueObject<String> {
        public Street(String value) {
            super(value);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static class PostalCode extends ValueObject<Integer> {
        public PostalCode(Integer value) {
            super(value);
        }
    }
}
