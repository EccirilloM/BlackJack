package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.repository.NotificaRepository;
import it.polimi.blackjackbe.repository.RicaricaRepository;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.RicaricaServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RicaricaServiceImplementationTest {

    @Mock
    UserRepository userRepository;

    @Mock
    TabacchiRepository tabacchiRepository;

    @Mock
    NotificaRepository notificaRepository;

    @Mock
    RicaricaRepository ricaricaRepository;

    @InjectMocks
    RicaricaServiceImplementation ricaricaServiceImplementation;

    @Test
    void richiediRicaricaSuccessful(){

    }
}
