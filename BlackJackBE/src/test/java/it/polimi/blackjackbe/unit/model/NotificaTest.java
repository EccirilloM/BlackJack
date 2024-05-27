package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.model.Notifica;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class NotificaTest {

    @Test
    void testNotifica() {
        Notifica notifica = new Notifica(1L, LocalDateTime.now(), "Testo", new User());
        Long notificaId = notifica.getNotificaId();
    }
}
