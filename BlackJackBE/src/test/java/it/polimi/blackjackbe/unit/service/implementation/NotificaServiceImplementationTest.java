package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.repository.NotificaRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.NotificaServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotificaServiceImplementationTest {

    @Mock
    NotificaRepository notificaRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    NotificaServiceImplementation notificaServiceImplementation;

}
