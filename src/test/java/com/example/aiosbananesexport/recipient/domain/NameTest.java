package com.example.aiosbananesexport.recipient.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {
    @ParameterizedTest
    @CsvSource({
            "firstName, lastName, firstName,    lastName,   true",
            "firstName, lastName, firstName2,   lastName,   false",
            "firstName, lastName, firstName,    lastName2,  false",
            "firstName, lastName, firstName2,   lastName2,  false",
    })
    void equality_test(String firstName1, String lastName1, String firstName2, String lastName2, boolean expectedEqual) {
        Name name1 = new Name(new Name.FirstName(firstName1), new Name.LastName(lastName1));
        Name name2 = new Name(new Name.FirstName(firstName2), new Name.LastName(lastName2));

        if (!expectedEqual) {
            assertThat(name1).isNotEqualTo(name2);
            return;
        }

        assertThat(name1).isEqualTo(name2);
    }
}
