package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.builder.CartaBuilder;
import it.polimi.blackjackbe.model.Carta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartaTest {

    @Test
    void testCarta() {
        Carta carta = new CartaBuilder().seme("cuori").valore("A").punteggio(1).build();
        String seme = carta.getSeme();
        String valore = carta.getValore();
        int punteggio = carta.getPunteggio();
        carta.setOrder(1);
        int order = carta.getOrder();
        Carta carta2 = new CartaBuilder().seme("cuori").valore("A").punteggio(1).build();
    }
}
