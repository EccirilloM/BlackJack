package it.polimi.blackjackbe.unit.controller;

import it.polimi.blackjackbe.controller.NotificaController;
import it.polimi.blackjackbe.service.implementation.NotificaServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class NotificaControllerTest {

    @Mock
    NotificaServiceImplementation notificaServiceImplementation;

    @InjectMocks
    NotificaController notificaController;

    @Test
    void getAllByUserId(){
        assertAll(() -> notificaController.getAllByUserId("1"));
    }
}
