package it.polimi.blackjackbe.unit.command.implementation;

import it.polimi.blackjackbe.builder.TavoloBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.command.implementation.Stay;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.repository.UserRepository;
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
public class StayTest {

    @Mock
    TavoloServiceImplementation tavoloServiceImplementation;
    @Mock
    Tavolo tavolo = new TavoloBuilder().player(new UserBuilder().saldo(10.0).build()).build();
    @InjectMocks
    Stay stay;

}
