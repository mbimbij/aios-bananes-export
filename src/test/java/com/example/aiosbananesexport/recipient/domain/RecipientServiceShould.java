package com.example.aiosbananesexport.recipient.domain;

import com.example.aiosbananesexport.recipient.exception.RecipientAlreadyExistsException;
import com.example.aiosbananesexport.recipient.exception.RecipientNotFoundException;
import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void create_a_recipient__in_nominal_case() {
        // GIVEN
        Recipient expectedRecipient = new Recipient(recipientId, name, address);

        // WHEN
        recipientService.createRecipient(name, address);

        // THEN
        Optional<Recipient> actualRecipient = recipientRepository.getById(recipientId);
        assertThat(actualRecipient).hasValue(expectedRecipient);
    }

    @Test
    void throw_an_exception__when_trying_to_create_a_recipient__but_user_exists_with_same_attributes() {
        // GIVEN
        recipientService.createRecipient(name, address);

        // WHEN / THEN
        assertThatThrownBy(() -> recipientService.createRecipient(name, address))
                .isInstanceOf(RecipientAlreadyExistsException.class);
    }

    @Test
    void rename_an_existing_recipient() {
        // GIVEN
        recipientService.createRecipient(name, address);
        Name newName = new Name(new Name.FirstName("newFirstName"),
                                new Name.LastName("lastName"));

        Recipient expectedRecipient = new Recipient(recipientId, newName, address);

        // WHEN
        recipientService.renameRecipient(recipientId, newName);

        // THEN
        Optional<Recipient> actualRecipient = recipientRepository.getById(recipientId);
        assertThat(actualRecipient).hasValue(expectedRecipient);
    }

    @Test
    void throw_an_exception__when_renaming_a_non_existing_recipient() {
        // GIVEN
        recipientService.createRecipient(name, address);
        Name newName = new Name(new Name.FirstName("newFirstName"),
                                new Name.LastName("lastName"));

        Recipient expectedRecipient = new Recipient(recipientId, newName, address);

        // WHEN / THEN
        assertThatThrownBy(() -> recipientService.renameRecipient(new RecipientId("nonExistingId"), newName))
                .isInstanceOf(RecipientNotFoundException.class);
    }

    @Test
    void delete_an_existing_recipient() {
        // GIVEN
        recipientService.createRecipient(name, address);

        // WHEN
        recipientService.deleteRecipient(recipientId);

        // THEN
        Optional<Recipient> actualRecipient = recipientRepository.getById(recipientId);
        assertThat(actualRecipient).isEmpty();
    }

    @Test
    void throw_an_exception__when_trying_to_delete_a_non_existing_recipient() {
        // GIVEN
        recipientService.createRecipient(name, address);

        // WHEN / THEN
        assertThatThrownBy(() -> recipientService.deleteRecipient(new RecipientId("nonExistingId")))
                .isInstanceOf(RecipientNotFoundException.class);
    }
}
