package it.polimi.blackjackbe.unit.command.implementation;

import it.polimi.blackjackbe.builder.TavoloBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.command.implementation.Deal;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class DealTest {

    @Mock
    UserRepository userRepository;
    @Mock
    TavoloServiceImplementation tavoloServiceImplementation;
    @Mock
    SingletonTavoli singletonTavoli = SingletonTavoli.getInstance();
    @InjectMocks
    Deal deal;

}
