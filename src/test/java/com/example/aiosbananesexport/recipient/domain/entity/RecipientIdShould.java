package com.example.aiosbananesexport.recipient.domain.entity;

import com.example.aiosbananesexport.recipient.domain.entity.RecipientId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecipientIdShould {
    @Test
    void be_equal_to_another_recipient_id__when_same_value() {
        RecipientId recipientId1 = new RecipientId("id1");
        RecipientId recipientId2 = new RecipientId("id1");

        assertThat(recipientId1).isEqualTo(recipientId2);
    }

    @Test
    void not_be_equal_to_another_recipient_id__when_different_value() {
        RecipientId recipientId1 = new RecipientId("id1");
        RecipientId recipientId2 = new RecipientId("id2");

        assertThat(recipientId1).isNotEqualTo(recipientId2);
    }
}
