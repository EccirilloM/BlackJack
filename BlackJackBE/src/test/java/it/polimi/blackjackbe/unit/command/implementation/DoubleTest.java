package it.polimi.blackjackbe.unit.command.implementation;

import it.polimi.blackjackbe.builder.CartaBuilder;
import it.polimi.blackjackbe.command.implementation.Double;
import it.polimi.blackjackbe.command.implementation.Hit;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.model.*;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoubleTest {

    @Mock
    TavoloServiceImplementation tavoloServiceImplementation;
    @Mock
    UserRepository userRepository;
    @Mock
    Hit hit;
    @InjectMocks
    Double doubleCommand;


    @Test
    void doubleSuccessful() {
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Fiori").valore("K").punteggio(10).build(),
                        new CartaBuilder().seme("Picche").valore("Q").punteggio(10).build(),
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build()
                ))
        );
        tavolo.setPlayer(user);
        tavolo.setPlotUser(1.0);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(hit.execute(any(), any())).thenReturn(new TavoloStatusResponse());

        doubleCommand.execute(user.getUserId(), Map.of());

        verify(hit, times(1)).execute(any(), any());
    }

    @Test
    void winSuccessful(){
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Fiori").valore("K").punteggio(10).build(),
                        new CartaBuilder().seme("Picche").valore("Q").punteggio(10).build(),
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build()
                ))
        );
        tavolo.setPlayer(user);
        tavolo.setPlotUser(1.0);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        TavoloStatusResponse tavoloStatusResponse = new TavoloStatusResponse();
        tavoloStatusResponse.setTavoloStatus(TavoloStatus.CONTINUE);
        when(hit.execute(any(), any())).thenReturn( tavoloStatusResponse);

        doubleCommand.execute(user.getUserId(), Map.of());
    }
}
