package it.polimi.blackjackbe.unit.builder;

import it.polimi.blackjackbe.builder.RicaricaBuilder;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class RicaricaBuilderTest {

    @InjectMocks
    RicaricaBuilder ricaricaBuilder;

    @Test
    void testAllMethods(){
        assertAll(() -> ricaricaBuilder
                .ricaricaId(1L)
                .importo(10.0)
                .dataRicarica(LocalDateTime.now())
                .dataRichiesta(LocalDateTime.now().plusHours(1))
                .richiesta(true)
                .accettata(true)
                .player(new User())
                .tabacchi(new Tabacchi())
                .build());

    }
}
