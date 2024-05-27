package it.polimi.blackjackbe.unit.command;

import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.command.CommandExecutor;
import it.polimi.blackjackbe.command.implementation.Deal;
import it.polimi.blackjackbe.command.implementation.Hit;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandExecutorTest {

    @Mock
    ApplicationContext applicationContext;
    @InjectMocks
    CommandExecutor commandExecutor;
}
