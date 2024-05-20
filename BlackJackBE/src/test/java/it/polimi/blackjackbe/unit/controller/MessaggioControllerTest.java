package it.polimi.blackjackbe.unit.controller;

import it.polimi.blackjackbe.controller.MessaggioController;
import it.polimi.blackjackbe.dto.request.MessaggioRequest;
import it.polimi.blackjackbe.service.implementation.MessaggioServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class MessaggioControllerTest {

    @Mock
    MessaggioServiceImplementation messaggioServiceImplementation;

    @InjectMocks
    MessaggioController messaggioController;

    @Test
    void getAllMessageByTipoTavolo(){
        assertAll(() -> messaggioController.getAllMessageByTipoTavolo("BASE"));
    }

    @Test
    void invia(){
        MessaggioRequest request = new MessaggioRequest("Testo", 1L, "BASE");
        assertAll(() -> messaggioController.invia(request));
    }
}
