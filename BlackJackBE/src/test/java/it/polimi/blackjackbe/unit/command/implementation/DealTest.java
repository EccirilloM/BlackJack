package it.polimi.blackjackbe.unit.command.implementation;

import it.polimi.blackjackbe.builder.CartaBuilder;
import it.polimi.blackjackbe.command.implementation.Deal;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DealTest {
    @Mock
    TavoloServiceImplementation tavoloServiceImplementation;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    Deal deal;


    @Test
    void executeSuccessful() {
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
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(new TavoloStatusResponse());

        deal.execute(user.getUserId(), Map.of("plot", 1));

        verify(tavoloServiceImplementation, times(1)).getTavoloStatusResponse(any(), any());
    }

    @Test
    void executeThrowsinvalidPlot(){
        User user = new User();
        user.setUserId(1);
        assertThrows(BadRequestException.class, () -> deal.execute(user.getUserId(), Map.of("plot", "test")));
    }

    @Test
    void executeThrowsinvalidUser(){
        User user = new User();
        user.setUserId(1);
        assertThrows(BadRequestException.class, () -> deal.execute(user.getUserId(), Map.of("plot", "1")));
    }

    @Test
    void executeThrowsnotPossiblePlot(){
        User user = new User();
        user.setUserId(1);
        user.setSaldo(100.00);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(IllegalStateException.class, () -> deal.execute(user.getUserId(), Map.of("plot", "1000")));

    }
}
