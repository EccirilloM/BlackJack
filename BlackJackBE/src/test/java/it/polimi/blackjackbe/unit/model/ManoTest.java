package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.model.Mano;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ManoTest {

    @Test
    void testMano() {
        Mano mano = new Mano();
        mano.setManoId(1L);
    }
}
