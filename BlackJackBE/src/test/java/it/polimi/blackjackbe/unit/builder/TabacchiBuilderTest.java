package it.polimi.blackjackbe.unit.builder;

import it.polimi.blackjackbe.builder.TabacchiBuilder;
import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class TabacchiBuilderTest {

    @InjectMocks
    TabacchiBuilder tabacchiBuilder;

    @Test
    void testTabacchiBuilder(){
        assertAll(()-> tabacchiBuilder
                .tabacchiId(1L)
                .nome("Tabacchi")
                .lat(1.0)
                .lng(1.0)
                .ricariche(List.of(new Ricarica()))
                .economo(new User())
                .build());
    }
}
