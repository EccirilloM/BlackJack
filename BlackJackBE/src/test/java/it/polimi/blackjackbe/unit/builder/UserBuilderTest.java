package it.polimi.blackjackbe.unit.builder;

import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class UserBuilderTest {

    @InjectMocks
    UserBuilder userBuilder;

    @Test
    void testAllMethods(){
        assertAll(() -> userBuilder
                .userId(1)
                .nome("Nome")
                .cognome("Cognome")
                .email("Ettore@gmail.com")
                .password("12345678")
                .username("Username")
                .ruolo(Ruolo.PLAYER)
                .dataNascita(LocalDateTime.now())
                .dataRegistrazione(LocalDateTime.now())
                .saldo(0.0)
                .tavoli(List.of(new Tavolo()))
                .messaggi(List.of(new Messaggio()))
                .ricariche(List.of(new Ricarica()))
                .tabacchi(List.of(new Tabacchi()))
                .notifiche(List.of(new Notifica()))
                .build()
        );

    }
}
