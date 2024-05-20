package it.polimi.blackjackbe.unit.builder;

import it.polimi.blackjackbe.builder.NotificaBuilder;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class NotificaBuilderTest {
    @InjectMocks
    NotificaBuilder notificaBuilder;

    @Test
    void testAllMethods(){

        assertAll(() -> notificaBuilder
                .notificaId(1L)
                .data(LocalDateTime.now())
                .testo("Testo")
                .player(new User())
                .build());
    }
}
