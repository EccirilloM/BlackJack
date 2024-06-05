package it.polimi.blackjackbe.unit.command.implementation;

import it.polimi.blackjackbe.builder.CartaBuilder;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HitTest {

    @Mock
    TavoloServiceImplementation tavoloServiceImplementation;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    Hit hit;


    @Test
    void hitSuccessful() {
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("Fiori").valore("5").punteggio(5).build(),
                        new CartaBuilder().seme("Picche").valore("Q").punteggio(10).build()
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        hit.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }

    @Test
    void winSuccessful(){
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarteSingolaManoPlayer(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("Fiori").valore("5").punteggio(5).build()
                ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("5").punteggio(5).build()
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        hit.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }

    @Test
    void loseSuccessful(){
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarteSingolaManoPlayer(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("Fiori").valore("5").punteggio(5).build()
                ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("K").punteggio(10).build()
                ))
        );
        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());
        tavolo.setPlayer(user);
        hit.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }
}
