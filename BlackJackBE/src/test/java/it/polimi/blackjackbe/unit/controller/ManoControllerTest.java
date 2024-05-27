package it.polimi.blackjackbe.unit.controller;

import it.polimi.blackjackbe.controller.ManoController;
import it.polimi.blackjackbe.service.definition.ManoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class ManoControllerTest {

    @Mock
    ManoService manoService;
    @InjectMocks
    ManoController manoController;

    @Test
    void getAllMani() {
        assertAll(() -> manoController.getAllMani());
    }

    @Test
    void getAllManiByUserId() {
        assertAll(() -> manoController.getAllMani("1"));
    }
}
