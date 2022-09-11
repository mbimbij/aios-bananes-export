package com.example.aiosbananesexport.common;

import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValueObjectShould {
    @Test
    void be_equal_to_another_value_object__when_same_type_and_same_value() {
        ValueObject1 object1 = new ValueObject1("val");
        ValueObject1 object2 = new ValueObject1("val");

        assertThat(object1).isEqualTo(object2);
    }

    @Test
    void not_be_equal_to_another_value_object__when_same_type_but_different_value() {
        ValueObject1 object1 = new ValueObject1("val");
        ValueObject1 object2 = new ValueObject1("other val");

        assertThat(object1).isNotEqualTo(object2);
    }

    /**
     * Need to use {@code @EqualsAndHashCode(callSuper = true)} on classes inheriting from {@code ValueObject} to avoid different subclasses with that value to be equal
     */
    @Test
    void not_be_equal_to_another_value_object__when_different_type_and_same_value() {
        ValueObject1 object1 = new ValueObject1("val");
        ValueObject2 object2 = new ValueObject2("val");

        assertThat(object1).isNotEqualTo(object2);
    }

    @EqualsAndHashCode(callSuper = true)
    public static class ValueObject1 extends ValueObject<String> {
        public ValueObject1(String value) {
            super(value);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static class ValueObject2 extends ValueObject<String> {
        public ValueObject2(String value) {
            super(value);
        }
    }
}
