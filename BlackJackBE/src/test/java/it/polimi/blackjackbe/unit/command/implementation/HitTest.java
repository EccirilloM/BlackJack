package it.polimi.blackjackbe.unit.command.implementation;

import it.polimi.blackjackbe.builder.TavoloBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.command.implementation.Hit;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HitTest {

    @Mock
    TavoloServiceImplementation tavoloServiceImplementation;
    @Mock
    Tavolo tavolo = new TavoloBuilder().player(new UserBuilder().saldo(10.0).build()).build();
    @InjectMocks
    Hit hit;

    @Test
    void execute() {
        TavoloStatusResponse tavoloStatusResponse = new TavoloStatusResponse(null, 0, null, 0, TavoloStatus.PLAYER_WIN, null, null);
        when(tavoloServiceImplementation.getTavolo(1L)).thenReturn(tavolo);
        when(tavoloServiceImplementation.getTavoloStatusResponse(any(), any())).thenReturn(tavoloStatusResponse);
        assertAll(() -> hit.execute(1L, null));
    }
}
