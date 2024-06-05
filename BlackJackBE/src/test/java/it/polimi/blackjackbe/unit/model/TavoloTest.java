package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TavoloTest {

    @Test
    void testTavolo() {
        Tavolo t = new Tavolo(1L, TipoTavolo.BASE, 10.0, new User(), List.of(), List.of(), List.of(), List.of(), 0.0, 0);
        t.initCarte();
        t.pescaCarta();
        t.punteggioUtente();
        t.punteggioDealer();
        t.end();
    }
}
