package it.polimi.blackjackbe.unit.command.implementation;

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
    void testStay() {
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarteSingolaManoPlayer(
            new ArrayList<>(List.of(
                    new Carta("Cuori", "A", 11),
                    new Carta("Fiori", "5", 5)
            ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "10", 10),
                        new Carta("Fiori", "K", 10)
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        stay.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }

    @Test
    void testWinInstant(){
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarteSingolaManoPlayer(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "A", 11),
                        new Carta("Fiori", "5", 5)
                ))
        );
        tavolo.setCarteSingolaManoDealer(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "A", 11),
                        new Carta("Fiori", "2", 2)
                ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "10", 10),
                        new Carta("Fiori", "K", 10)
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        stay.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }

    @Test
    void testWin(){
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarteSingolaManoPlayer(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "A", 11),
                        new Carta("Fiori", "9", 9)
                ))
        );
        tavolo.setCarteSingolaManoDealer(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "A", 11),
                        new Carta("Fiori", "5", 2)
                ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "4", 4),
                        new Carta("Fiori", "K", 10)
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        stay.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }

    @Test
    void testDraw(){
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        SingletonTavoli.getInstance().createTable(user, TipoTavolo.BASE);
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user);
        tavolo.setCarteSingolaManoPlayer(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "A", 11),
                        new Carta("Fiori", "9", 9)
                ))
        );
        tavolo.setCarteSingolaManoDealer(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "A", 11),
                        new Carta("Fiori", "5", 2)
                ))
        );
        tavolo.setCarte(
                new ArrayList<>(List.of(
                        new Carta("Cuori", "7", 7),
                        new Carta("Fiori", "K", 10)
                ))
        );
        tavolo.setPlayer(user);

        when(tavoloServiceImplementation.getTavolo(any())).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        stay.execute(user.getUserId(), Map.of());

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }



}
