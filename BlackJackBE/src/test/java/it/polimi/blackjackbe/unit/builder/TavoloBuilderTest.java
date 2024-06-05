package it.polimi.blackjackbe.unit.builder;

import it.polimi.blackjackbe.builder.CartaBuilder;
import it.polimi.blackjackbe.builder.TavoloBuilder;
import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TavoloBuilderTest {

    @InjectMocks
    TavoloBuilder tavoloBuilder;

    @Test
    void testTavoloBuilder() {
        Tavolo tavolo = new TavoloBuilder()
                .tipoTavolo(TipoTavolo.BASE)
                .carte(List.of(new CartaBuilder().build()))
                .carteSingolaMano(List.of(new CartaBuilder().build()))
                .cartaDealer(List.of(new CartaBuilder().build()))
                .build();
    }
}
