package it.polimi.blackjackbe.unit.builder;

import it.polimi.blackjackbe.builder.CartaBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartaBuilderTest {

    @InjectMocks
    CartaBuilder cartaBuilder;

    @Test
    void testCartaBuilder() {
        CartaBuilder cartaBuilder = new CartaBuilder();
        cartaBuilder.seme("cuori")
                    .valore("A")
                    .punteggio(1)
                    .order(1)
                    .build();
        String seme = cartaBuilder.getSeme();
        String valore = cartaBuilder.getValore();
        int punteggio = cartaBuilder.getPunteggio();
        int order = cartaBuilder.getOrder();
    }
}
