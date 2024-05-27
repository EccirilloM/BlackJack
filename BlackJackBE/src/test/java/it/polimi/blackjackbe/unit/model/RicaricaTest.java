package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class RicaricaTest {

    @Test
    public void testRicarica() {
        Ricarica ricarica = new Ricarica(1L, 10.0, LocalDateTime.now(), LocalDateTime.now(), true, true, new User(), new Tabacchi());
    }
}
