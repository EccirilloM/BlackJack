package it.polimi.blackjackbe.unit.controller;

import it.polimi.blackjackbe.command.CommandExecutor;
import it.polimi.blackjackbe.controller.TavoloController;
import it.polimi.blackjackbe.service.definition.TavoloService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TavoloControllerTest {

    @Mock
    TavoloService tavoloService;
    @Mock
    CommandExecutor commandExecutor;
    @InjectMocks
    TavoloController tavoloController;

    @Test
    void init() {
        assertAll(() -> tavoloController.init("tipoTavolo", 1L));
    }

    @Test
    void end() {
        assertAll(() -> tavoloController.end(1L, null));
    }

    @Test
    void command() {
        when(commandExecutor.executeCommand("command", 1L, null)).thenReturn(null);
        assertAll(() -> tavoloController.command("command", 1L, null));
    }

    @Test
    void getCommands() {
        when(commandExecutor.getCommandNames()).thenReturn(null);
        assertAll(() -> tavoloController.getCommands());
    }
}
