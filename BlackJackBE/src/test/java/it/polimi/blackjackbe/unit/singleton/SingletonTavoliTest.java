package it.polimi.blackjackbe.unit.singleton;

import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SingletonTavoliTest {

    @Mock
    Map<Long, Tavolo> tavoliAttivi = new HashMap<>();
    @InjectMocks
    SingletonTavoli singletonTavoli = SingletonTavoli.getInstance();

    @Test
    void getTable() {
        when(tavoliAttivi.get(1L)).thenReturn(new Tavolo());
        assertAll(() -> singletonTavoli.getTable(new UserBuilder().userId(1L).build()));
    }

    @Test
    void createTable() {

        assertAll(() -> singletonTavoli.createTable(new UserBuilder().userId(1L).build(), null));
    }
}
