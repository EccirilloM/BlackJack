package it.polimi.blackjackbe.unit.builder;

import it.polimi.blackjackbe.builder.MessaggioBuilder;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class MessaggioBuilderTest {

    @InjectMocks
    MessaggioBuilder messaggioBuilder;

    @Test
    void testAllMethods(){
        assertAll(() -> messaggioBuilder
                .messaggioId(1L)
                .testo("Testo")
                .createdAt(LocalDateTime.now())
                .user(new User())
                .tipoTavolo(TipoTavolo.BASE)
                .build());
    }
}
