package it.polimi.blackjackbe.unit.command.implementation;

import it.polimi.blackjackbe.builder.CartaBuilder;
import it.polimi.blackjackbe.command.implementation.Stay;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StayTest {

    @Mock
    TavoloServiceImplementation tavoloServiceImplementation;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    Stay stay;


    @Test
    void staySuccessful() {
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
                        new CartaBuilder().seme("Cuori").valore("10").punteggio(10).build(),
                        new CartaBuilder().seme("Fiori").valore("K").punteggio(10).build()
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        stay.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }

    @Test
    void winInstantSuccessful(){
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
        tavolo.setCarteSingolaManoDealer(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("Fiori").valore("2").punteggio(2).build()
                ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("10").punteggio(10).build(),
                        new CartaBuilder().seme("Fiori").valore("K").punteggio(10).build()
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        stay.execute(user.getUserId(), Map.of());

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
                        new CartaBuilder().seme("Fiori").valore("9").punteggio(9).build()
                ))
        );
        tavolo.setCarteSingolaManoDealer(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("Fiori").valore("5").punteggio(2).build()
                ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("4").punteggio(4).build(),
                        new CartaBuilder().seme("Fiori").valore("K").punteggio(10).build()
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        stay.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }

    @Test
    void drawSuccessful(){
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarteSingolaManoPlayer(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("Fiori").valore("9").punteggio(9).build()
                ))
        );
        tavolo.setCarteSingolaManoDealer(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("Fiori").valore("5").punteggio(2).build()
                ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new CartaBuilder().seme("Cuori").valore("7").punteggio(7).build(),
                        new CartaBuilder().seme("Fiori").valore("K").punteggio(10).build()
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        stay.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }



}
