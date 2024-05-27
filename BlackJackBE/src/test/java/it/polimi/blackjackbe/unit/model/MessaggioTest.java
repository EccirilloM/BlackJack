package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.model.Messaggio;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class MessaggioTest {

    @Test
    void testMessaggio() {
        Messaggio messaggio = new Messaggio(1L, "testo", LocalDateTime.now(), TipoTavolo.BASE, new User());
        Long messaggioId = messaggio.getMessaggioId();
    }
}
