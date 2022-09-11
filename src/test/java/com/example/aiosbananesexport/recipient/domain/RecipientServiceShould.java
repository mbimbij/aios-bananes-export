package com.example.aiosbananesexport.recipient.domain;

import com.example.aiosbananesexport.recipient.exception.RecipientAlreadyExistsException;
import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

public class RecipientServiceShould {

    private final RecipientId recipientId = new RecipientId("id");
    private Name name;
    private Address address;
    private InMemoryRecipientRepository recipientRepository;
    private RecipientService recipientService;

    @BeforeEach
    void setUp() {
        name = new Name(new Name.FirstName("firstName"),
                             new Name.LastName("lastName"));

        address = new Address(new Address.Street("address"),
                                      new Address.PostalCode(75019),
                                      new Address.City("Paris"),
                                      new Address.Country("France"));

        recipientRepository = Mockito.spy(new InMemoryRecipientRepository());
        Mockito.doReturn(recipientId)
               .when(recipientRepository)
               .generateRecipientId();

        recipientService = new RecipientService(recipientRepository);
    }

    @Test
    void create_a_recipient__when_no_existing_user_with_same_attributes() {
        // GIVEN
        Recipient expectedRecipient = new Recipient(recipientId, name, address);

        // WHEN
        recipientService.createRecipient(name, address);

        // THEN
        StepVerifier.create(recipientRepository.getById(recipientId))
                    .expectNext(expectedRecipient)
                    .verifyComplete();
    }

    @Test
    void throw_an_exception__when_trying_to_create_a_recipient__but_user_exists_with_same_attributes() {
        // GIVEN
        recipientService.createRecipient(name, address);

        // WHEN - THEN
        Assertions.assertThatThrownBy(() -> recipientService.createRecipient(name, address))
                  .isInstanceOf(RecipientAlreadyExistsException.class);
    }
}
