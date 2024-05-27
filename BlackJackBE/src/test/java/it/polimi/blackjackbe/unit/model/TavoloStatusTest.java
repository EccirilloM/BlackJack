package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.model.TavoloStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TavoloStatusTest {

    @Test
    void testTavoloStatus() {
        TavoloStatus s1 = TavoloStatus.DRAW;
        TavoloStatus s2 = TavoloStatus.PLAYER_LOSE;
        TavoloStatus s3 = TavoloStatus.PLAYER_WIN;
        TavoloStatus s4 = TavoloStatus.CONTINUE;
    }
}
