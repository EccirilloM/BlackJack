package it.polimi.blackjackbe.unit.config;

import it.polimi.blackjackbe.config.SaldoConfig;
import it.polimi.blackjackbe.service.definition.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class SaldoConfigTest {

    @Mock
    UserService userService;

    @InjectMocks
    SaldoConfig saldoConfig;

    @Test
    void ricaricaSaldoTest(){
        assertAll(() -> saldoConfig.ricaricaSaldo());
    }
}
