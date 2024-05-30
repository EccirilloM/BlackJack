package it.polimi.blackjackbe.unit.command;

import it.polimi.blackjackbe.command.CommandExecutor;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;


@ExtendWith(MockitoExtension.class)
public class CommandExecutorTest {

    @Mock
    ApplicationContext applicationContext;
    @InjectMocks
    CommandExecutor commandExecutor;
}
