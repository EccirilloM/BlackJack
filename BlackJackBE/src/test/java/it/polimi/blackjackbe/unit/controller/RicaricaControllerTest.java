package it.polimi.blackjackbe.unit.controller;

import it.polimi.blackjackbe.controller.RicaricaController;
import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.service.definition.RicaricaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class RicaricaControllerTest {

    @Mock
    private RicaricaService ricaricaService;

    @InjectMocks
    private RicaricaController ricaricaController;
}
