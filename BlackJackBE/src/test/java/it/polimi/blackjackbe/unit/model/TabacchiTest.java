package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.model.Tabacchi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TabacchiTest {

    @Test
    public void testTabacchi() {
        Tabacchi t = new Tabacchi(1L, "Tabacchi", 1.0, 1.0, null, null);
        Long tabacchiId = t.getTabacchiId();
    }
}
